package com.jay.wechat.protocol.response;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * MessageResponsePacket
 *
 * @author xuanjian
 */
@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
