package netty.protocol.request;

import lombok.Data;
import netty.protocol.Packet;

import static netty.protocol.command.Command.LOGIN_REQUEST;

/**
 * LoginRequestPacket 登录请求数据包
 *
 * @author xuanjian.xuwj
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
