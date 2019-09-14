package com.jay.wechat.client.handler;

import com.jay.wechat.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * JoinGroupResponseHandler
 *
 * @author xuanjian
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {
        if (joinGroupResponsePacket.isSuccess()) {
            System.out.println("加入群[" + joinGroupResponsePacket.getGroupId() + "]成功");
        } else {
            System.out.println("加入群[" + joinGroupResponsePacket.getGroupId() + "]失败, 原因是: " + joinGroupResponsePacket.getReason());
        }
    }
}
