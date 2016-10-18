package me.frontiere.protocol.packet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Overwatch on 18.10.2016.
 */
public class HandlerList {
    private volatile List<Handler> handlers = new ArrayList<>();

    public List<Handler> asList() {
        return handlers;
    }

    public void addHandler(Handler handler) {
        if(handlers.contains(handler))
            return;
        handlers.add(handler);
        handler.init();
    }

    public void removeHandler(Handler handler) {
        if(!handlers.contains(handler))
            return;
        handlers.remove(handler);
    }
}
