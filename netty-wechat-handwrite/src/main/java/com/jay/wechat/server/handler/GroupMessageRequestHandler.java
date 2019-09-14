package com.jay.wechat.server.handler;

import com.jay.wechat.protocol.request.GroupMessageRequestPacket;
import com.jay.wechat.protocol.response.GroupMessageResponsePacket;
import com.jay.wechat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * GroupMessageRequestHandler
 *
 * @author xuanjian
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    private GroupMessageRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {

        String groupId = groupMessageRequestPacket.getGroupId();

        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setGroupId(groupId);
        groupMessageResponsePacket.setFromUser(SessionUtil.getSession(ctx.channel()));
        groupMessageResponsePacket.setMessage(groupMessageRequestPacket.getMessage());

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(groupMessageResponsePacket);
    }
}
