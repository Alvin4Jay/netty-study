package com.jay.wechat.server.handler;

import com.jay.wechat.protocol.request.ListGroupMembersRequestPacket;
import com.jay.wechat.protocol.response.ListGroupMembersResponsePacket;
import com.jay.wechat.session.Session;
import com.jay.wechat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * ListGroupMembersRequestHandler
 *
 * @author xuanjian
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket listGroupMembersRequestPacket) throws Exception {
        // 1. 获取群的 ChannelGroup
        String groupId = listGroupMembersRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        ListGroupMembersResponsePacket listGroupMembersResponsePacket = new ListGroupMembersResponsePacket();
        if (channelGroup != null) {
            // 2. 遍历群成员的 channel，对应的 session，构造群成员的信息
            List<Session> sessionList = new ArrayList<>();
            for (Channel channel : channelGroup) {
                Session session = SessionUtil.getSession(channel);
                sessionList.add(session);
            }
            listGroupMembersResponsePacket.setSuccess(true);
            listGroupMembersResponsePacket.setGroupId(groupId);
            listGroupMembersResponsePacket.setSessionList(sessionList);
        } else {
            listGroupMembersResponsePacket.setSuccess(false);
            listGroupMembersResponsePacket.setReason("群不存在!");
        }

        // 3.写回响应
        ctx.channel().writeAndFlush(listGroupMembersResponsePacket);
    }
}
