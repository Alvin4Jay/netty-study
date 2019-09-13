package com.jay.wechat.protocol.response;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.LOGOUT_RESPONSE;

/**
 * LogoutResponsePacket
 *
 * @author xuanjian
 */
@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
