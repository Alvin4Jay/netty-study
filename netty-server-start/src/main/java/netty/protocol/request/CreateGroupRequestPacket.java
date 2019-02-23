package netty.protocol.request;

import lombok.Data;
import netty.protocol.Packet;

import java.util.List;

import static netty.protocol.command.Command.CREATE_GROUP_REQUEST;

/**
 * 创建群聊的请求数据包
 *
 * @author xuanjian.xuwj
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
