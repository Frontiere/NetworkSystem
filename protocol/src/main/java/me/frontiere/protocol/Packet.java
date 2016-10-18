package me.frontiere.protocol;

import io.netty.buffer.ByteBuf;

/**
 * Class Created by Traape | Jan.
 * 18.10.2016
 */
public abstract class Packet {

    public abstract void read( ByteBuf byteBuf );

    public abstract void write( ByteBuf byteBuf );

}
