package com.jay.wechat.protocol.response;

import com.jay.wechat.protocol.Packet;
import com.jay.wechat.session.Session;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

/**
 * GroupMessageResponsePacket
 *
 * @author xuanjian
 */
@Data
public class GroupMessageResponsePacket extends Packet {

    private String groupId;

    private Session fromUser;

    private String message;

    @Override
    public byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
