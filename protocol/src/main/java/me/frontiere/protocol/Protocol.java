package me.frontiere.protocol;

import lombok.Getter;
import me.frontiere.protocol.packet.Packet;
import me.frontiere.protocol.packets.MessagePacket;

/**
 * Created by Overwatch on 18.10.2016.
 */
@Getter
public enum Protocol {
    MESSAGE_PACKET(1, MessagePacket.class);

    private int packetId;
    private Class<? extends Packet> packetClass;

    Protocol(int packetId, Class<? extends Packet> packetClass) {
        this.packetId = packetId;
        this.packetClass = packetClass;
    }

    public static Class<? extends Packet> getPacketFromId(int packetId) {
        for(Protocol protocol : values())
            if(protocol.getPacketId() == packetId)
                return protocol.getPacketClass();
        return null;
    }

    public static int getIdFromPacket(Class<? extends Packet> packetClass) {
        for(Protocol protocol : values())
            if(protocol.getPacketClass() == packetClass)
                return protocol.getPacketId();
        return -1;
    }
}
