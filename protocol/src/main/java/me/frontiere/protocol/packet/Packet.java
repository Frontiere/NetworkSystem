package me.frontiere.protocol.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Class Created by Traape | Jan.
 * 18.10.2016
 */
@NoArgsConstructor
@AllArgsConstructor
public abstract class Packet {
    public abstract void read( ByteBuf byteBuf );
    public abstract void write( ByteBuf byteBuf );
}
