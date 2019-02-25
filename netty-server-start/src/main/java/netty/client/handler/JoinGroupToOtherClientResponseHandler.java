package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.response.JoinGroupToOtherClientResponsePacket;

/**
 * 加群响应处理器(通知给群内其他客户端)。类似 xxx 加入群聊 yyy
 *
 * @author xuanjian.xuwj
 */
public class JoinGroupToOtherClientResponseHandler extends SimpleChannelInboundHandler<JoinGroupToOtherClientResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupToOtherClientResponsePacket responsePacket) {
        System.out.println(responsePacket.getSession().getUserId() + " 加入群聊 " + responsePacket.getGroupId());
    }
}
