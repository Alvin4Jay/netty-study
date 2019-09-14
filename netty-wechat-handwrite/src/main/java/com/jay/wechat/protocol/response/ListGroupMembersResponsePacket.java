package com.jay.wechat.protocol.response;

import com.jay.wechat.protocol.Packet;
import com.jay.wechat.session.Session;
import lombok.Data;

import java.util.List;

import static com.jay.wechat.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

/**
 * ListGroupMembersResponsePacket
 *
 * @author xuanjian
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    private boolean success;

    private String reason;

    @Override
    public byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
