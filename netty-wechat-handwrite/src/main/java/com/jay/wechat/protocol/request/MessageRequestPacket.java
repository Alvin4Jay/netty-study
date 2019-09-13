package com.jay.wechat.protocol.request;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.MESSAGE_REQUEST;

/**
 * MessageRequestPacket
 *
 * @author xuanjian
 */
@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
