package nia.chapter4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ChannelOperationExample
 *
 * @author xuanjian
 */
public class ChannelOperationExample {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * 写出数据
     */
    public static void writeToChannel() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ByteBuf data = Unpooled.copiedBuffer("Hi world", CharsetUtil.UTF_8);

        ChannelFuture future = channel.writeAndFlush(data);
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("write success!");
                } else {
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });
    }

    /**
     * 多线程写出数据
     */
    public static void writeToChannelInMultiThreads() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ByteBuf data = Unpooled.copiedBuffer("Hi world", CharsetUtil.UTF_8);

        Runnable runnable = () -> channel.writeAndFlush(data.duplicate());

        Executor executor = Executors.newCachedThreadPool();

        executor.execute(runnable);

        executor.execute(runnable);
    }

}
