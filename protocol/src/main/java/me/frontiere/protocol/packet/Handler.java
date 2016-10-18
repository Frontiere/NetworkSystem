package me.frontiere.protocol.packet;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Overwatch on 18.10.2016.
 */
public class Handler {
    public final HashMap<Class<?>, Method> handlerMethods = new HashMap<>();

    public final void init() {
        for(Method method : this.getClass().getMethods()) {
            if(method.getAnnotationsByType(PacketHandler.class).length == 0)
                continue;
            handlerMethods.put(method.getParameterTypes()[1], method);
        }
    }

    public final Method getHandlerMethod(Packet packet) {
        if(handlerMethods.containsKey(packet.getClass()))
            return handlerMethods.get(packet.getClass());
        return null;
    }
}
