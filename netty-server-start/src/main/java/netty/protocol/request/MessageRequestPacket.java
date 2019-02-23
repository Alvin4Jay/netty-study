package netty.protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import netty.protocol.Packet;

import static netty.protocol.command.Command.MESSAGE_REQUEST;

/**
 * 消息发送对象
 *
 * @author xuanjian.xuwj
 */
@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toUserId;

    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
