package netty.protocol.command;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import netty.serialize.Serializer;
import netty.serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import static netty.protocol.command.Command.LOGIN_REQUEST;

/**
 * PacketCodec
 *
 * @author xuanjian.xuwj
 */
public class PacketCodec {
    // 魔数
    private static final int MAGIC_NUMBER = 0x12345678;
    // 指令--包类型
    private static final Map<Byte, Class<? extends Packet>> PACKET_TYPE_MAP;
    // 序列化算法--序列化器
    private static final Map<Byte, Serializer> SERIALIZER_MAP;

    static {
        PACKET_TYPE_MAP = new HashMap<>();
        PACKET_TYPE_MAP.put(LOGIN_REQUEST, LoginRequestPacket.class);

        SERIALIZER_MAP = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        SERIALIZER_MAP.put(serializer.getSerializeAlgorithm(), serializer);
    }

    public ByteBuf encode(Packet packet) {
        // 1.获取BuyeBuf
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 2.Packet序列化
        byte[] data = Serializer.DEFAULT.serialize(packet);

        // 3.编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializeAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过魔数校验
        byteBuf.skipBytes(4);

        // 跳过通讯协议版本
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializerAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据长度
        int length = byteBuf.readInt();

        // 数据读取
        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        Class<? extends Packet> clazz = getRequestType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);

        if (clazz != null && serializer != null) {
            return serializer.deserialize(clazz, data);
        }
        return null;
    }

    /**
     * 获取数据包类型
     *
     * @param command
     * @return
     */
    private Class<? extends Packet> getRequestType(Byte command) {
        return PACKET_TYPE_MAP.get(command);
    }

    /**
     * 根据序列化算法，获取序列化器
     *
     * @param serializerAlgorithm
     * @return
     */
    private Serializer getSerializer(Byte serializerAlgorithm) {
        return SERIALIZER_MAP.get(serializerAlgorithm);
    }

}
