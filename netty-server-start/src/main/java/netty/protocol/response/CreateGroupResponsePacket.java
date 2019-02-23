package netty.protocol.response;

import lombok.Data;
import netty.protocol.Packet;

import java.util.List;

import static netty.protocol.command.Command.CREATE_GROUP_RESPONSE;

/**
 * 创建群聊的响应数据包
 *
 * @author xuanjian.xuwj
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;
    // 群聊id
    private String groupId;
    // 群用户
    private List<String> usernameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
