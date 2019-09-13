package com.jay.wechat.protocol.request;

import com.jay.wechat.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.jay.wechat.protocol.command.Command.MESSAGE_REQUEST;

/**
 * MessageRequestPacket
 *
 * @author xuanjian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toUserId;

    private String message;

    @Override
    public byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
