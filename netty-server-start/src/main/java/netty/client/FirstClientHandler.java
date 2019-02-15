package netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Client ChannelInBoundHandler
 *
 * @author xuanjian.xuwj
 */
@Deprecated
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    // 客户端连接上服务端的时候回调
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端写出数据 -> " + "你好，玄翦");

        // 1. 获取数据
        ByteBuf byteBuf = getByteBuf(ctx, "你好，玄翦");

        // 2. 写数据
        ctx.channel().writeAndFlush(byteBuf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, String msg) {
        // 1. 获取二进制抽象 ByteBuf
        ByteBuf byteBuf = ctx.alloc().buffer();

        // 2. 准备数据，指定字符串的字符集为 utf-8
        byte[] data = msg.getBytes(Charset.forName("UTF-8"));

        // 3. 填充数据到 ByteBuf
        byteBuf.writeBytes(data);

        return byteBuf;
    }

    // 读取服务端发来的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
//        System.out.println("refCnt(): " + byteBuf.refCnt());
        System.out.println(new Date() + ": 客户端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

        // 思考题
//        byteBuf = getByteBuf(ctx, "客户端已连接至服务端");
//
//        ctx.channel().writeAndFlush(byteBuf);
    }
}
