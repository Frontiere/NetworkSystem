package me.frontiere.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.frontiere.protocol.Protocol;
import me.frontiere.protocol.packet.Packet;

import java.util.List;

/**
 * Class Created by Traape | Jan.
 * 18.10.2016
 */
public class PacketDecoder extends ByteToMessageDecoder {
    protected void decode( ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list ) throws Exception {
        if ( byteBuf instanceof EmptyByteBuf ) {
            return;
        }
        int packetID = byteBuf.readInt();
        Class<? extends Packet> packetClass = Protocol.getPacketFromId( packetID );
        if ( packetClass != null ) {
            Packet packet = packetClass.newInstance();
            packet.read( new ByteBufInputStream(byteBuf));
            list.add( packet );
        }
    }
}
