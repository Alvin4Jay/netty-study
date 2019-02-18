package netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * 粘包问题演示
 *
 * @author xuanjian.xuwj
 */
@Deprecated
public class FirstClientHandlerForStickyBagDemo extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 连续发送数据
        for (int i = 0; i < 1000; i++) {
            ByteBuf byteBuf = getByteBuf(ctx, "你好，欢迎关注我的微信公众号，《闪电侠的博客》!");

            ctx.channel().writeAndFlush(byteBuf);
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, String msg) {
        ByteBuf byteBuf = ctx.alloc().buffer();
        byte[] data = msg.getBytes(Charset.forName("UTF-8"));
        byteBuf.writeBytes(data);

        return byteBuf;
    }
}
