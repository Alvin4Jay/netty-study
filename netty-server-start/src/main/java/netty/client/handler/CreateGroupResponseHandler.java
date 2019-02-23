package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.response.CreateGroupResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;

import java.util.List;

/**
 * 创建群聊的响应处理器
 *
 * @author xuanjian.xuwj
 */
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket createGroupResponsePacket) {
        Session session = SessionUtil.getSession(ctx.channel());

        List<String> usernameList = createGroupResponsePacket.getUsernameList();
        usernameList.remove(session.getUsername()); // 显示时移除自己的名字
        System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + usernameList);
    }
}
