package com.jay.wechat.server.handler;

import com.jay.wechat.protocol.request.JoinGroupRequestPacket;
import com.jay.wechat.protocol.response.JoinGroupResponsePacket;
import com.jay.wechat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * JoinGroupRequestHandler
 *
 * @author xuanjian
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    private JoinGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket joinGroupRequestPacket) throws Exception {
        String groupId = joinGroupRequestPacket.getGroupId();
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 添加进去
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        // 2. 构造加群响应发送给客户端
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        if (channelGroup != null) {
            channelGroup.add(ctx.channel());
            joinGroupResponsePacket.setGroupId(groupId);
            joinGroupResponsePacket.setSuccess(true);
        } else {
            joinGroupResponsePacket.setSuccess(false);
            joinGroupResponsePacket.setReason("群不存在!");
        }

        ctx.channel().writeAndFlush(joinGroupResponsePacket);
    }
}
