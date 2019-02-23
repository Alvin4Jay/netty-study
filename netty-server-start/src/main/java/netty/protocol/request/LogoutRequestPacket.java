package netty.protocol.request;

import lombok.Data;
import netty.protocol.Packet;

import static netty.protocol.command.Command.LOGOUT_REQUEST;

/**
 * LogoutRequestPacket 登出请求数据包
 *
 * @author xuanjian.xuwj
 */
@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
