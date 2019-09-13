package com.jay.wechat.protocol.request;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.jay.wechat.protocol.command.Command.CREATE_GROUP_REQUEST;

/**
 * CreateGroupRequestPacket
 *
 * @author xuanjian
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
