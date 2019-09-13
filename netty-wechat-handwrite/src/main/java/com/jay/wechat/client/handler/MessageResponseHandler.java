package com.jay.wechat.client.handler;

import com.jay.wechat.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * MessageResponseHandler
 *
 * @author xuanjian
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
    }
}
