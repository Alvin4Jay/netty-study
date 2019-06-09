package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 从Channel引导客户端(8-5)，共享事件循环
 *
 * @author xuanjian
 */
public class BootstrapSharingEventLoopGroup {

    public static void main(String[] args) {
        BootstrapSharingEventLoopGroup server = new BootstrapSharingEventLoopGroup();
        server.bootstrap();
    }

    private void bootstrap() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    ChannelFuture channelFuture;

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.channel(NioSocketChannel.class).handler(new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                System.out.println("Received Data");
                            }
                        });
                        // 共享事件循环，避免创建线程和线程上下文切换
                        bootstrap.group(ctx.channel().eventLoop());
                        channelFuture = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        if (channelFuture.isDone()) {
                            // do something with the data
                        }
                    }
                });

        ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bound attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }

}
