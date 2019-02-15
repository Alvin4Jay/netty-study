package netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.MessageRequestPacket;
import netty.protocol.response.MessageResponsePacket;

import java.util.Date;

/**
 * 消息请求指令数据包的处理器
 *
 * @author xuanjian.xuwj
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        // 处理消息
        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务端回复: [" + messageRequestPacket.getMessage() + "]");
        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
