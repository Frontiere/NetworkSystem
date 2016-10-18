package me.frontiere.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.frontiere.protocol.Protocol;
import me.frontiere.protocol.packet.Packet;

/**
 * Created by Overwatch on 18.10.2016.
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          Packet packet,
                          ByteBuf byteBuf) throws Exception {
        int packetId = Protocol.getIdFromPacket(packet.getClass());
        if(packetId > -1) {
            byteBuf.writeInt(packetId);
            packet.write(new ByteBufOutputStream(byteBuf));
        }
    }
}
