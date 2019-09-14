package com.jay.wechat.protocol.response;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.QUIT_GROUP_RESPONSE;

/**
 * Class description here.
 *
 * @author xuanjian
 */
@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
