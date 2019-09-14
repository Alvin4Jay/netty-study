package com.jay.wechat.protocol.request;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.QUIT_GROUP_REQUEST;

/**
 * QuitGroupRequestPacket
 *
 * @author xuanjian
 */
@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
