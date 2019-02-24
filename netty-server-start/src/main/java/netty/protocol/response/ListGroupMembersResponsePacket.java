package netty.protocol.response;

import lombok.Data;
import netty.protocol.Packet;
import netty.session.Session;

import java.util.List;

import static netty.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

/**
 * 获取群聊成员的响应数据包
 *
 * @author xuanjian.xuwj
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;
    // 用户信息
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
