package me.frontiere.server.handler;

import io.netty.channel.ChannelHandlerContext;
import me.frontiere.protocol.packet.Handler;
import me.frontiere.protocol.packet.PacketHandler;
import me.frontiere.protocol.packets.MessagePacket;

/**
 * Created by Overwatch on 18.10.2016.
 */
public class MessagePacketHandler extends Handler {
    @PacketHandler
    public void onPacketInput(ChannelHandlerContext ctx, MessagePacket packet) {
        System.out.println(packet.getString());
    }
}
