package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.LoginRequestPacket;
import netty.protocol.response.LoginResponsePacket;
import netty.util.LoginUtil;

import java.util.Date;
import java.util.UUID;

/**
 * 登录响应指令数据报的处理器
 *
 * @author xuanjian.xuwj
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    // 客户端连接上服务端的时候，直接发送登录请求
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录...");

        // 1. 构建登录请求数据包
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("netty-study");
        loginRequestPacket.setPassword("pwd");

        // 2.写数据到服务端
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            System.out.println(new Date() + ": 客户端登录成功...");
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因是: " + loginResponsePacket.getReason());
        }
    }
}
