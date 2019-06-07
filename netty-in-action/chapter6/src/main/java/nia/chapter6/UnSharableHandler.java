package nia.chapter6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <code>@Sharable</code>的错误用法
 *
 * @author xuanjian
 */
@ChannelHandler.Sharable
public class UnSharableHandler extends ChannelInboundHandlerAdapter {
    // 线程不安全
    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        count++;
        System.out.println("channelRead() called the " + count + " time");
        ctx.fireChannelRead(msg);
    }
}
