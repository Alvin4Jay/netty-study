package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import netty.protocol.request.QuitGroupRequestPacket;
import netty.protocol.response.QuitGroupResponsePacket;
import netty.protocol.response.QuitGroupToOtherClientResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;

/**
 * 退出群聊请求的处理器
 *
 * @author xuanjian.xuwj
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    private QuitGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket) {
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 移除
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());

        // 2. 构造退群响应发送给客户端
        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        quitGroupResponsePacket.setSuccess(true);
        quitGroupResponsePacket.setGroupId(groupId);

        ctx.writeAndFlush(quitGroupResponsePacket);

        // 3.通知到群内其他客户端
        QuitGroupToOtherClientResponsePacket quitGroupToOtherClientResponsePacket = new QuitGroupToOtherClientResponsePacket();
        quitGroupToOtherClientResponsePacket.setGroupId(groupId);
        Session session = SessionUtil.getSession(ctx.channel());
        quitGroupToOtherClientResponsePacket.setSession(session);
        channelGroup.writeAndFlush(quitGroupToOtherClientResponsePacket);

        System.out.println(session.getUserId() + " 退出群聊 " + groupId);

        // 当一个群的人数为 0 的时候，清理掉内存中群相关的信息
        if (channelGroup.size() == 0) {
            SessionUtil.unbindChannelGroup(groupId);
        }
    }
}
