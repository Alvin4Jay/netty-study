package com.jay.wechat.client.handler;

import com.jay.wechat.protocol.response.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ListGroupMembersResponseHandler
 *
 * @author xuanjian
 */
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket listGroupMembersResponsePacket) throws Exception {
        if (listGroupMembersResponsePacket.isSuccess()) {
            System.out.println("获取群[" + listGroupMembersResponsePacket.getGroupId()
                    + "]成员列表成功, 成员有: " + listGroupMembersResponsePacket.getSessionList());
        } else {
            System.out.println("获取群[" + listGroupMembersResponsePacket.getGroupId()
                    + "]成员列表失败, 原因是: " + listGroupMembersResponsePacket.getReason());
        }
    }
}
