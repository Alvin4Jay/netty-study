package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.response.LogoutResponsePacket;
import netty.util.SessionUtil;

/**
 * 登出响应指令数据包的处理器
 *
 * @author xuanjian.xuwj
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) {
        SessionUtil.unbindSession(ctx.channel());
    }
}
