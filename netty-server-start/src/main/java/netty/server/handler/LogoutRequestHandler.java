package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.LogoutRequestPacket;
import netty.protocol.response.LogoutResponsePacket;
import netty.util.SessionUtil;

/**
 * 登出请求处理器
 *
 * @author xuanjian.xuwj
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) {
        SessionUtil.unbindSession(ctx.channel());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        ctx.writeAndFlush(logoutResponsePacket); // 优化点 参考 https://juejin.im/book/5b4bc28bf265da0f60130116/section/5b4db131e51d4519634fb867
    }
}
