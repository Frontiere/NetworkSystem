package me.frontiere.protocol;

import lombok.Getter;

/**
 * Created by Overwatch on 18.10.2016.
 */
public class Client extends PacketManager {
    @Getter
    public ChannelHandler channelHandler;

    public void onEnable() {
    }

    public void onDisable() {
    }
}
