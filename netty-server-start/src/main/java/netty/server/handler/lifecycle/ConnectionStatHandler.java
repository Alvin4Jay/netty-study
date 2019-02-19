package netty.server.handler.lifecycle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 统计客户端连接数，每隔1s打印
 *
 * @author xuanjian.xuwj
 */
public class ConnectionStatHandler extends ChannelInboundHandlerAdapter {
    public static final AtomicLong CONNECTIONS = new AtomicLong(0);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CONNECTIONS.incrementAndGet();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        CONNECTIONS.decrementAndGet();
        super.channelInactive(ctx);
    }
}
