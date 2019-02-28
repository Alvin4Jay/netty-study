package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.response.GroupMessageResponsePacket;
import netty.session.Session;

/**
 * 处理群聊消息响应的处理器
 *
 * @author xuanjian.xuwj
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket resposnePacket) {
        String fromGroupId = resposnePacket.getFromGroupId();
        Session fromUser = resposnePacket.getFromUser();
        String message = resposnePacket.getMessage();
        System.out.println("收到群[" + fromGroupId + "]中[" + fromUser + "]发来的消息：" + message);
    }
}
