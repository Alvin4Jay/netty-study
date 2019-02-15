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

    private String message;

    public MessageRequestPacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
