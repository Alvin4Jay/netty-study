package com.jay.wechat.client.handler;

import com.jay.wechat.protocol.response.LoginResponsePacket;
import com.jay.wechat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * LogoutResponseHandler
 *
 * @author xuanjian
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        SessionUtil.unbindSession(ctx.channel());
    }
}
