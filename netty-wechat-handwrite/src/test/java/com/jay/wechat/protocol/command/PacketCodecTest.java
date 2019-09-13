package com.jay.wechat.protocol.command;

import com.jay.wechat.protocol.Packet;
import com.jay.wechat.protocol.PacketCodec;
import com.jay.wechat.protocol.request.LoginRequestPacket;
import com.jay.wechat.serialize.Serializer;
import com.jay.wechat.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;

import java.util.UUID;


/**
 * Class description here.
 *
 * @author xuanjian
 */
public class PacketCodecTest {

    @org.junit.Test
    public void encode() {
        Serializer serializer = JSONSerializer.INSTANCE;
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodec packetCodec = PacketCodec.INSTANCE;
        ByteBuf byteBuf = packetCodec.encode(ByteBufAllocator.DEFAULT.ioBuffer(), loginRequestPacket);
        Packet decodedPacket = packetCodec.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }

    @org.junit.Test
    public void decode() {
    }
}