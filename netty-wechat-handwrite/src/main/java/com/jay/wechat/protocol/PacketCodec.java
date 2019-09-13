package com.jay.wechat.protocol;

import com.jay.wechat.protocol.request.LoginRequestPacket;
import com.jay.wechat.protocol.request.MessageRequestPacket;
import com.jay.wechat.protocol.response.LoginResponsePacket;
import com.jay.wechat.protocol.response.MessageResponsePacket;
import com.jay.wechat.serialize.Serializer;
import com.jay.wechat.serialize.SerializerAlgorithm;
import com.jay.wechat.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.jay.wechat.protocol.command.Command.LOGIN_REQUEST;
import static com.jay.wechat.protocol.command.Command.LOGIN_RESPONSE;
import static com.jay.wechat.protocol.command.Command.MESSAGE_REQUEST;
import static com.jay.wechat.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * PacketCodec
 *
 * @author xuanjian
 */
public class PacketCodec {

    public static final PacketCodec INSTANCE = new PacketCodec();
    public static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> PACKET_MAP = new HashMap<>(16);
    private static final Map<Byte, Serializer> SERIALIZER_MAP = new HashMap<>(4);

    static {
        PACKET_MAP.put(LOGIN_REQUEST, LoginRequestPacket.class);
        PACKET_MAP.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        PACKET_MAP.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        PACKET_MAP.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        SERIALIZER_MAP.put(SerializerAlgorithm.JSON, JSONSerializer.INSTANCE);
    }

    private PacketCodec() {
    }

    private Serializer getSerializer(byte serializerAlgorithm) {
        return SERIALIZER_MAP.get(serializerAlgorithm);
    }

    private Class<? extends Packet> getPacketType(byte command) {
        return PACKET_MAP.get(command);
    }

    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // 1. 序列化 java 对象
        byte[] data = Serializer.DEFAULT.serialize(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);
        // 序列化算法
        byte serializerAlgorithm = byteBuf.readByte();
        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();
        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        Serializer serializer = getSerializer(serializerAlgorithm);
        Class<? extends Packet> clazz = getPacketType(command);
        if (serializer != null && clazz != null) {
            return serializer.deserialize(clazz, data);
        }
        return null;
    }

}
