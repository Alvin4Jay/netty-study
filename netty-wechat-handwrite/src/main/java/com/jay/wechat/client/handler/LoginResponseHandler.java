package com.jay.wechat.client.handler;

import com.jay.wechat.protocol.response.LoginResponsePacket;
import com.jay.wechat.session.Session;
import com.jay.wechat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * LoginResponseHandler
 *
 * @author xuanjian
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        String userId = loginResponsePacket.getUserId();
        String username = loginResponsePacket.getUsername();
        if (loginResponsePacket.isSuccess()) {
            // 标记为已登录
            SessionUtil.bindSession(new Session(userId, username), ctx.channel());
            System.out.println("[" + username + "]登陆成功, userId为: " + userId);
        } else {
            System.out.println("[" + username + "]登录失败, 原因是: " + loginResponsePacket.getReason());
        }
    }

}
