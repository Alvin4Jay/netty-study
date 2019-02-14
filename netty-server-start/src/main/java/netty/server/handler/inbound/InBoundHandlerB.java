package netty.server.handler.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.attribute.Attributes;

import java.util.Date;

/**
 * InBoundHandlerB 入站事件的传播
 *
 * @author xuanjian.xuwj
 */
public class InBoundHandlerB extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerB: " + msg);

        // System.out.println(ctx.channel().attr(Attributes.HANDLER_FINISH_TIME).get()); // 问题二
        // ctx.channel().attr(Attributes.HANDLER_FINISH_TIME)
        // .set("InBoundHandlerB is done: " + System.currentTimeMillis()); // 问题二

        super.channelRead(ctx, msg);
    }
}
