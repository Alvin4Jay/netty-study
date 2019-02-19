package netty.server.handler.lifecycle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 统计客户端的入口流量，以字节为单位
 *
 * @author xuanjian.xuwj
 */
public class InboundTrafficStatHandler extends ChannelInboundHandlerAdapter {
    private static final AtomicLong BYTES = new AtomicLong(0);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BYTES.addAndGet(((ByteBuf) msg).readableBytes());
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("total inbound traffic in bytes: " + BYTES.longValue());
        super.channelReadComplete(ctx);
    }
}
