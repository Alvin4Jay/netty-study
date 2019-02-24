package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.response.JoinGroupResponsePacket;

/**
 * 加入群聊响应的处理器
 *
 * @author xuanjian.xuwj
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket responsePacket) {
        if (responsePacket.isSuccess()) {
            System.out.println("加入群[" + responsePacket.getGroupId() + "]成功!");
        } else {
            System.out.println("加入群[" + responsePacket.getGroupId() + "]失败，原因是：" + responsePacket.getReason());
        }
    }
}
