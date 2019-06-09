package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.OioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * 引导DatagramChannel 8-8
 *
 * @author xuanjian
 */
public class BootstrapDatagramChannel {
    public static void main(String[] args) {
        BootstrapDatagramChannel boot = new BootstrapDatagramChannel();
        boot.bootstrap();
    }

    private void bootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new OioEventLoopGroup())
                .channel(OioDatagramChannel.class)
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
                        // do something with the packet
                    }
                });
        // 调用bind()方法
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(0));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Channel bound");
                } else {
                    System.err.println("Bind attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
