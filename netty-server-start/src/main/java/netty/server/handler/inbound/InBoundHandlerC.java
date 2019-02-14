package netty.server.handler.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.attribute.Attributes;
import netty.server.handler.outbound.OutBoundHandlerC;

/**
 * InBoundHandlerC 入站事件的传播
 *
 * @author xuanjian.xuwj
 */
public class InBoundHandlerC extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerC: " + msg);
        // ctx.pipeline().remove(OutBoundHandlerC.class); // 问题一
        // System.out.println(ctx.channel().attr(Attributes.HANDLER_FINISH_TIME).get()); // 问题二
        ctx.channel().writeAndFlush(msg);
    }
}
