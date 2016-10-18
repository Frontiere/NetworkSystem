package me.frontiere.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import me.frontiere.protocol.*;
import me.frontiere.protocol.pipeline.PacketDecoder;
import me.frontiere.protocol.pipeline.PacketEncoder;
import me.frontiere.server.handler.MessagePacketHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class Created by Traape | Jan.
 * 18.10.2016
 */
public class FrontiereServer extends PacketManager {
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private String host;
    private int port;

    public FrontiereServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void onEnable() {
        registerHandler(new MessagePacketHandler());
    }

    public void start() {
        service.execute(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                preparePipeline(socketChannel);
                            }
                        })
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                ChannelFuture future = bootstrap.bind(host, port).sync();
                future.channel().closeFuture().sync();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        });
    }

    private ChannelHandler preparePipeline(Channel channel) {
        ChannelHandler channelHandler = new me.frontiere.protocol.ChannelHandler(this);

        channel.pipeline().addLast(new LengthFieldPrepender(4, true));
        channel.pipeline().addLast(new PacketDecoder());
        channel.pipeline().addLast(new PacketEncoder());
        channel.pipeline().addLast(channelHandler);
        return channelHandler;
    }

    public static void main( String[] args ) {
        FrontiereServer mainServer = new FrontiereServer("localhost", 13337);
        mainServer.start();
        mainServer.onEnable();
    }
}
