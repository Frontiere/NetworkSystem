package me.frontiere.protocol;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import me.frontiere.protocol.packet.Handler;
import me.frontiere.protocol.packet.HandlerList;
import me.frontiere.protocol.packet.Packet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Overwatch on 18.10.2016.
 */
public class PacketManager {
    @Getter
    private final HandlerList handlers = new HandlerList();

    public void registerHandler(Handler handler) {
        handlers.addHandler(handler);
    }

    public void unregisterHandler(Handler handler) {
        handlers.removeHandler(handler);
    }

    public void firePacket(ChannelHandlerContext ctx, Packet packet) {
        for(Handler handler : handlers.asList()) {
            Method method = handler.getHandlerMethod(packet);
            if(method != null)
                try {
                    method.invoke(handler, ctx, packet);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
        }
    }
}
