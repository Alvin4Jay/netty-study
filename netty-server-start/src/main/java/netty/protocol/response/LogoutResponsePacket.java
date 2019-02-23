package netty.protocol.response;

import lombok.Data;
import netty.protocol.Packet;

import static netty.protocol.command.Command.LOGOUT_RESPONSE;

/**
 * LogoutResponsePacket 登出响应数据包
 *
 * @author xuanjian.xuwj
 */
@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
