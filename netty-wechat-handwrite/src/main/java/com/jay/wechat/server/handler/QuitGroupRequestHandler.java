package com.jay.wechat.server.handler;

import com.jay.wechat.protocol.request.QuitGroupRequestPacket;
import com.jay.wechat.protocol.response.QuitGroupResponsePacket;
import com.jay.wechat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * QuitGroupRequestHandler
 *
 * @author xuanjian
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    private QuitGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        String groupId = quitGroupRequestPacket.getGroupId();
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 移除
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        // 2. 构造退群响应发送给客户端
        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        if (channelGroup != null) {
            channelGroup.remove(ctx.channel());
            quitGroupResponsePacket.setGroupId(groupId);
            quitGroupResponsePacket.setSuccess(true);

            if (channelGroup.size() == 0) {
                SessionUtil.unbindChannelGroup(groupId);
            }
        } else {
            quitGroupResponsePacket.setSuccess(false);
            quitGroupResponsePacket.setReason("群不存在!");
        }

        ctx.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
