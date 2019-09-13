package com.jay.wechat.protocol.command;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.LOGIN_REQUEST;

/**
 * LoginRequestPacket
 *
 * @author xuanjian
 */
@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;

    @Override
    public byte getCommand() {
        return LOGIN_REQUEST;
    }
}
