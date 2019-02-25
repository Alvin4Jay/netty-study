package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.response.QuitGroupToOtherClientResponsePacket;

/**
 * 退群响应处理器(通知给群内其他客户端)。类似 xxx 退出群聊 yyy
 *
 * @author xuanjian.xuwj
 */
public class QuitGroupToOtherClientResponseHandler extends SimpleChannelInboundHandler<QuitGroupToOtherClientResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupToOtherClientResponsePacket responsePacket) {
        System.out.println(responsePacket.getSession().getUserId() + " 退出群聊 " + responsePacket.getGroupId());
    }
}
