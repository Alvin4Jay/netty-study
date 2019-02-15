package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.response.MessageResponsePacket;

import java.util.Date;

/**
 * 消息响应指令数据包的处理器
 *
 * @author xuanjian.xuwj
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        // 处理消息
        System.out.println(new Date() + ": 收到服务端消息: " + messageResponsePacket.getMessage());
    }
}
