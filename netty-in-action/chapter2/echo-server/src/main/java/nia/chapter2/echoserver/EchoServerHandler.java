package nia.chapter2.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * nia.chapter2.echoserver.EchoServerHandler
 *
 * @author xuanjian
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private CountDownLatch countDownLatch;

    public EchoServerHandler(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        // ByteBuf.toString不修改读写指针
        System.out.println(new Date() + " Server received " + byteBuf.toString(CharsetUtil.UTF_8));

        ctx.write(byteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                future.channel().close().addListener(f -> {
                    if (f.isSuccess()) {
                        TimeUnit.SECONDS.sleep(1);
                        this.countDownLatch.countDown();
                    }
                });
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
