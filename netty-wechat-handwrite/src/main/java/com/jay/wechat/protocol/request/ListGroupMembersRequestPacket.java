package com.jay.wechat.protocol.request;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

/**
 * ListGroupMembersRequestPacket
 *
 * @author xuanjian
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
