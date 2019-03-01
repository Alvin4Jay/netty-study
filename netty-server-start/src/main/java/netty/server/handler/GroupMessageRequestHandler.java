package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import netty.protocol.request.GroupMessageRequestPacket;
import netty.protocol.response.GroupMessageResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;

/**
 * 处理群聊消息请求的处理器
 *
 * @author xuanjian.xuwj
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    private GroupMessageRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket requestPacket) {
        // 获取数据
        String groupId = requestPacket.getToGroupId();
        String message = requestPacket.getMessage();
        Session session = SessionUtil.getSession(ctx.channel());
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        // 响应写出
        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setFromGroupId(groupId);
        groupMessageResponsePacket.setMessage(message);
        groupMessageResponsePacket.setFromUser(session);

        channelGroup.writeAndFlush(groupMessageResponsePacket);
    }
}
