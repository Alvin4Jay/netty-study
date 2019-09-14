package com.jay.wechat.protocol.request;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.JOIN_GROUP_REQUEST;

/**
 * JoinGroupRequestPacket
 *
 * @author xuanjian
 */
@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
