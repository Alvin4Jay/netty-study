package netty.protocol.response;

import lombok.Data;
import netty.protocol.Packet;
import netty.session.Session;

import static netty.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

/**
 * 群聊消息响应的数据包
 *
 * @author xuanjian.xuwj
 */
@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
