package me.frontiere.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.Getter;
import me.frontiere.protocol.ChannelHandler;
import me.frontiere.protocol.PacketManager;
import me.frontiere.protocol.packets.MessagePacket;
import me.frontiere.protocol.pipeline.PacketDecoder;
import me.frontiere.protocol.pipeline.PacketEncoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class Created by Traape | Jan.
 * 18.10.2016
 */
public class FrontiereClient extends PacketManager {
    @Getter
    public ChannelHandler channelHandler;

    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private String host;
    private int port;

    public FrontiereClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void onEnable() {
        getChannelHandler().send(new MessagePacket("Das ist ein Test!"));
    }

    public void start() {
        service.execute(() -> {
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            protected void initChannel(SocketChannel socketChannel)
                                    throws Exception {
                                channelHandler = preparePipeline(socketChannel);
                                onEnable();
                            }
                        });
                ChannelFuture future = bootstrap.connect(host, port).sync();
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
        });
    }

    private ChannelHandler preparePipeline(Channel channel) {
        ChannelHandler channelHandler = new ChannelHandler(this);

        channel.pipeline().addLast(new LengthFieldPrepender(4, true));
        channel.pipeline().addLast(new PacketDecoder());
        channel.pipeline().addLast(new PacketEncoder());
        channel.pipeline().addLast(channelHandler);
        return channelHandler;
    }
    public static void main(String[] args) {
        FrontiereClient mainClient = new FrontiereClient("localhost", 13337);
        mainClient.start();
    }
}