package netty.protocol.command;

import io.netty.buffer.ByteBuf;
import netty.serialize.Serializer;
import netty.serialize.impl.JSONSerializer;
import org.junit.Assert;
import org.junit.Test;

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
        loginRequestPacket.setUserId(123);
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodec packetCodeC = new PacketCodec();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));
    }
}