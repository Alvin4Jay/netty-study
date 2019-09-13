package com.jay.wechat.server.handler;

import com.jay.wechat.protocol.request.LoginRequestPacket;
import com.jay.wechat.protocol.response.LoginResponsePacket;
import com.jay.wechat.session.Session;
import com.jay.wechat.util.IDUtil;
import com.jay.wechat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * LoginRequestHandler
 *
 * @author xuanjian
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        // 登录流程
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        String username = loginRequestPacket.getUsername();
        loginResponsePacket.setUsername(username);

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);

            String userId = IDUtil.randomId();
            SessionUtil.bindSession(new Session(userId, username), ctx.channel());
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + username + "]登陆成功");
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码校验失败");
            System.out.println("客户端登陆失败");
        }

        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 用户断线之后取消绑定
        SessionUtil.unbindSession(ctx.channel());
    }

    @SuppressWarnings("unused")
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

}
