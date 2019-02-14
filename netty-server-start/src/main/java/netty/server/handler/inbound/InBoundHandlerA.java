package netty.server.handler.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.attribute.Attributes;

import java.util.Date;

/**
 * InBoundHandlerA 入站事件的传播
 *
 * @author xuanjian.xuwj
 */
public class InBoundHandlerA extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerA: " + msg);
        // ctx.pipeline().remove(InBoundHandlerB.class); // 问题一
        // ctx.channel().attr(Attributes.HANDLER_FINISH_TIME)
        // .set("InBoundHandlerA is done: " + System.currentTimeMillis()); // 问题二
        super.channelRead(ctx, msg);
    }
}
