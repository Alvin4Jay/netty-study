package netty.protocol.command;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import netty.protocol.Packet;
import netty.protocol.PacketCodec;
import netty.protocol.request.LoginRequestPacket;
import netty.serialize.Serializer;
import netty.serialize.impl.JSONSerializer;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * PacketCodecTest
 *
 * @author xuanjian.xuwj
 */
public class PacketCodecTest {

    @Test
    public void encode() {
        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodec packetCodeC = PacketCodec.INSTANCE;
        ByteBuf byteBuf = packetCodeC.encode(ByteBufAllocator.DEFAULT, loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));
    }
}