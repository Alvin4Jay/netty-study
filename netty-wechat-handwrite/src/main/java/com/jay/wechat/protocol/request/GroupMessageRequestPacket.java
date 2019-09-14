package com.jay.wechat.protocol.request;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.GROUP_MESSAGE_REQUEST;

/**
 * GroupMessageRequestPacket
 *
 * @author xuanjian
 */
@Data
public class GroupMessageRequestPacket extends Packet {

    private String groupId;

    private String message;

    @Override
    public byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
