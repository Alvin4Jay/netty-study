package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.response.LoginResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;

/**
 * 登录响应指令数据报的处理器
 *
 * @author xuanjian.xuwj
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {
        String userId = loginResponsePacket.getUserId();
        String username = loginResponsePacket.getUsername();

        if (loginResponsePacket.isSuccess()) {
            System.out.println("[" + username + "]登陆成功，userId: " + userId);
            SessionUtil.bindSession(ctx.channel(), new Session(userId, username));
        } else {
            System.out.println("[" + username + "]登陆失败，原因是: " + loginResponsePacket.getReason());
        }
    }
}
