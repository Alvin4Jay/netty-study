package netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Server ChannelInBoundHandler
 *
 * @author xuanjian.xuwj
 */
@Deprecated
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    // 读取数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 服务端读取客户端发来的数据
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

        // 服务端回写数据给客户端
        System.out.println(new Date() + ": 服务端写出数据 -> " + "你好，欢饮关注我的博客");

        byteBuf = getByteBuf(ctx, "你好，欢饮关注我的博客");

        ctx.channel().writeAndFlush(byteBuf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, String msg) {
        ByteBuf byteBuf = ctx.alloc().buffer();

        byte[] data = msg.getBytes(Charset.forName("UTF-8"));

        byteBuf.writeBytes(data);

        return byteBuf;
    }

    // 思考题
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ByteBuf byteBuf = getByteBuf(ctx, "服务端接收到新连接");
//
//        ctx.channel().writeAndFlush(byteBuf);
//    }
}
