package me.frontiere.protocol.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * Class Created by Traape | Jan.
 * 18.10.2016
 */
@NoArgsConstructor
public abstract class Packet {
    public abstract void read( ByteBufInputStream byteBuf ) throws IOException;
    public abstract void write( ByteBufOutputStream byteBuf ) throws IOException;
}
