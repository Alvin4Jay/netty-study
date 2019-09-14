package com.jay.wechat.protocol.response;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.JOIN_GROUP_RESPONSE;

/**
 * JoinGroupResponsePacket
 *
 * @author xuanjian
 */
@Data
public class JoinGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
