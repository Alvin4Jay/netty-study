package com.jay.wechat.protocol.response;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.jay.wechat.protocol.command.Command.CREATE_GROUP_RESPONSE;

/**
 * CreateGroupResponsePacket
 *
 * @author xuanjian
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private String groupId;

    private List<String> usernameList;

    private boolean success;

    private String reason;

    @Override
    public byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
