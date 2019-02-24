package netty.protocol.request;

import lombok.Data;
import netty.protocol.Packet;

import static netty.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

/**
 * 获取群聊成员的请求数据包
 *
 * @author xuanjian.xuwj
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
