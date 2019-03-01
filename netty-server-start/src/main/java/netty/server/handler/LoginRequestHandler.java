package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.LoginRequestPacket;
import netty.protocol.response.LoginResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;

import java.util.Date;
import java.util.UUID;

/**
 * 登录请求指令数据包的处理器
 *
 * @author xuanjian.xuwj
 */
@ChannelHandler.Sharable /* 共享handler */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private LoginRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        System.out.println(new Date() + ": 收到客户端[" + loginRequestPacket.getUsername() + "]登录请求");
        // 登录校验
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUsername(loginRequestPacket.getUsername());

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            // 标记客户端为登录状态
            // LoginUtil.markAsLogin(ctx.channel());
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            SessionUtil.bindSession(ctx.channel(), new Session(userId, loginRequestPacket.getUsername()));
            System.out.println(new Date() + ": 客户端[" + loginRequestPacket.getUsername() + "]登录成功");
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码校验失败");
            System.out.println(new Date() + ": 客户端[" + loginRequestPacket.getUsername() + "]登录失败.");
        }

        // 登录响应
//        ctx.channel().writeAndFlush(loginResponsePacket);
        ctx.writeAndFlush(loginResponsePacket); // 优化点 参考 https://juejin.im/book/5b4bc28bf265da0f60130116/section/5b4db131e51d4519634fb867
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    // 用户断线之后取消绑定
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unbindSession(ctx.channel());
    }
}
