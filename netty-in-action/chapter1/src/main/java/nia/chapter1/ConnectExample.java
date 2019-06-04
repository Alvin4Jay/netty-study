package nia.chapter1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Listing 1.3 Asynchronous connect
 * <p>
 * Listing 1.4 Callback in action
 *
 * @author xuanjian
 */
public class ConnectExample {

    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void connect() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ChannelFuture future = channel.connect(new InetSocketAddress("192.168.0.1", 25));

        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    ByteBuf byteBuf = Unpooled.copiedBuffer("Hello", Charset.forName("UTF-8"));
                    future.channel().writeAndFlush(byteBuf);
                } else {
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });

    }


}
