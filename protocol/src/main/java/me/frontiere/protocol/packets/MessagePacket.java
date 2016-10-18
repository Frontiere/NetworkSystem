package me.frontiere.protocol.packets;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.frontiere.protocol.packet.Packet;

import java.io.IOException;

/**
 * Created by Overwatch on 18.10.2016.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessagePacket extends Packet {
    private String string;

    @Override
    public void read(ByteBufInputStream byteBuf) throws IOException {
        string = byteBuf.readUTF();
    }

    @Override
    public void write(ByteBufOutputStream byteBuf) throws IOException {
        byteBuf.writeUTF(string);
    }
}
