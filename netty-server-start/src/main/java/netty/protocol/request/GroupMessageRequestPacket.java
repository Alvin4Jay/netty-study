package netty.protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import netty.protocol.Packet;

import static netty.protocol.command.Command.GROUP_MESSAGE_REQUEST;

/**
 * 群聊消息发送的数据包
 *
 * @author xuanjian.xuwj
 */
@Data
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet {

    private String toGroupId;

    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
