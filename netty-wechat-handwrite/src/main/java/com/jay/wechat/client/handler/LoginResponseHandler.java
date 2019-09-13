package com.jay.wechat.client.handler;

import com.jay.wechat.protocol.request.LoginRequestPacket;
import com.jay.wechat.protocol.response.LoginResponsePacket;
import com.jay.wechat.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * LoginResponseHandler
 *
 * @author xuanjian
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登陆");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("flash");
        loginRequestPacket.setPassword("123");

        // 写出数据
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            // 标记为已登录
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println(new Date() + ": 客户端登陆成功");
        } else {
            System.out.println(new Date() + ": 客户端登录失败, 原因是: " + loginResponsePacket.getReason());
        }
    }
}
