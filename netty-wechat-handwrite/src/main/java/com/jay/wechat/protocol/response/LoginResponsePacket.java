package com.jay.wechat.protocol.response;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.LOGIN_RESPONSE;

/**
 * LoginResponsePacket
 *
 * @author xuanjian
 */
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
