package netty.protocol.response;

import lombok.Data;
import netty.protocol.Packet;

import static netty.protocol.command.Command.LOGIN_RESPONSE;

/**
 * LoginResponsePacket 登录响应数据包
 *
 * @author xuanjian.xuwj
 */
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
