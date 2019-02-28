package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import netty.protocol.request.JoinGroupRequestPacket;
import netty.protocol.response.JoinGroupResponsePacket;
import netty.protocol.response.JoinGroupToOtherClientResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;

/**
 * 加入群聊请求的处理器
 *
 * @author xuanjian.xuwj
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    private JoinGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) {
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 添加进去
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        ChannelGroup oldChannelGroup = new DefaultChannelGroup(ctx.executor());
        oldChannelGroup.addAll(channelGroup);
        channelGroup.add(ctx.channel());

        // 2. 构造加群响应发送给客户端
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        joinGroupResponsePacket.setSuccess(true);
        joinGroupResponsePacket.setGroupId(groupId);

        ctx.channel().writeAndFlush(joinGroupResponsePacket);

        // 3.通知到群内其他客户端
        JoinGroupToOtherClientResponsePacket joinGroupToOtherClientResponsePacket = new JoinGroupToOtherClientResponsePacket();
        joinGroupToOtherClientResponsePacket.setGroupId(groupId);
        Session session = SessionUtil.getSession(ctx.channel());
        joinGroupToOtherClientResponsePacket.setSession(session);
        oldChannelGroup.writeAndFlush(joinGroupToOtherClientResponsePacket);

        System.out.println(session.getUserId() + " 加入群聊 " + groupId);
    }
}
