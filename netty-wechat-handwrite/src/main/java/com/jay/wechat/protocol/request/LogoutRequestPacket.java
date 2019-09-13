package com.jay.wechat.protocol.request;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.LOGOUT_REQUEST;

/**
 * LogoutRequestPacket
 *
 * @author xuanjian
 */
@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
