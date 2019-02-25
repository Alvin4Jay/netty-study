package netty.protocol.response;

import lombok.Data;
import netty.protocol.Packet;
import netty.session.Session;

import static netty.protocol.command.Command.JOIN_GROUP_TO_OTHER_CLIENT_RESPONSE;

/**
 * 加入群聊响应数据包(通知给群内其他客户端)
 *
 * @author xuanjian.xuwj
 */
@Data
public class JoinGroupToOtherClientResponsePacket extends Packet {

    private String groupId;

    // 加入群聊的用户信息
    private Session session;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_TO_OTHER_CLIENT_RESPONSE;
    }
}
