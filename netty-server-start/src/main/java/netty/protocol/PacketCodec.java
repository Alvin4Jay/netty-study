package netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import netty.protocol.request.CreateGroupRequestPacket;
import netty.protocol.request.GroupMessageRequestPacket;
import netty.protocol.request.JoinGroupRequestPacket;
import netty.protocol.request.ListGroupMembersRequestPacket;
import netty.protocol.request.LoginRequestPacket;
import netty.protocol.request.LogoutRequestPacket;
import netty.protocol.request.MessageRequestPacket;
import netty.protocol.request.QuitGroupRequestPacket;
import netty.protocol.response.CreateGroupResponsePacket;
import netty.protocol.response.GroupMessageResponsePacket;
import netty.protocol.response.JoinGroupResponsePacket;
import netty.protocol.response.JoinGroupToOtherClientResponsePacket;
import netty.protocol.response.ListGroupMembersResponsePacket;
import netty.protocol.response.LoginResponsePacket;
import netty.protocol.response.LogoutResponsePacket;
import netty.protocol.response.MessageResponsePacket;
import netty.protocol.response.QuitGroupResponsePacket;
import netty.protocol.response.QuitGroupToOtherClientResponsePacket;
import netty.serialize.Serializer;
import netty.serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import static netty.protocol.command.Command.*;

/**
 * PacketCodec 单例
 *
 * @author xuanjian.xuwj
 */
public class PacketCodec {
    // 魔数
    public static final int MAGIC_NUMBER = 0x12345678;
    // 指令--包类型
    private final Map<Byte, Class<? extends Packet>> PACKET_TYPE_MAP;
    // 序列化算法--序列化器
    private final Map<Byte, Serializer> SERIALIZER_MAP;

    public static final PacketCodec INSTANCE = new PacketCodec();

    private PacketCodec() {
        PACKET_TYPE_MAP = new HashMap<>();
        PACKET_TYPE_MAP.put(LOGIN_REQUEST, LoginRequestPacket.class);
        PACKET_TYPE_MAP.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        PACKET_TYPE_MAP.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        PACKET_TYPE_MAP.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        PACKET_TYPE_MAP.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        PACKET_TYPE_MAP.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);
        PACKET_TYPE_MAP.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        PACKET_TYPE_MAP.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        PACKET_TYPE_MAP.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        PACKET_TYPE_MAP.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        PACKET_TYPE_MAP.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        PACKET_TYPE_MAP.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        PACKET_TYPE_MAP.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        PACKET_TYPE_MAP.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        PACKET_TYPE_MAP.put(JOIN_GROUP_TO_OTHER_CLIENT_RESPONSE, JoinGroupToOtherClientResponsePacket.class);
        PACKET_TYPE_MAP.put(QUIT_GROUP_TO_OTHER_CLIENT_RESPONSE, QuitGroupToOtherClientResponsePacket.class);
        PACKET_TYPE_MAP.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        PACKET_TYPE_MAP.put(GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);

        SERIALIZER_MAP = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        SERIALIZER_MAP.put(serializer.getSerializeAlgorithm(), serializer);
    }

    @Deprecated
    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        // 1.创建ByteBuf
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();

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

    public void encode(ByteBuf byteBuf, Packet packet) {
        // 1.Packet序列化
        byte[] data = Serializer.DEFAULT.serialize(packet);

        // 2.编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializeAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
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
