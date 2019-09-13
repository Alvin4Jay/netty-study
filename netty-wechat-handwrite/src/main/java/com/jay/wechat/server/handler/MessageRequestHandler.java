package com.jay.wechat.server.handler;

import com.jay.wechat.protocol.request.MessageRequestPacket;
import com.jay.wechat.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * MessageRequestHandler
 *
 * @author xuanjian
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        // 处理消息
        System.out.println(new Date() + ": 服务端收到消息: " + messageRequestPacket.getMessage());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务端回复消息->" + messageRequestPacket.getMessage());

        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
