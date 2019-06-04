package com.wacai.middleware.whatisnetty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * NettyServer
 *
 * @author xuanjian.xuwj
 */
public class NettyServer {
    public static void main(String[] args) {
        // 服务端引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 接收、创建新连接的线程
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 读取数据的线程
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("handlerAdded()--->ChannelInitializer");
                        System.out.println("channel registered--->ChannelInitializer: " + ctx.channel().isRegistered());
                        System.out.println("channel--->ChannelInitializer: " + ctx.channel().toString());
                        System.out.println("channel pipeline--->ChannelInitializer: " + ctx.channel().pipeline().toString());
                        super.handlerAdded(ctx);
                    }

                    @Override
                    protected void initChannel(NioSocketChannel ch) {
//                        ch.pipeline().addLast(new DemoHandler1());
//                        ch.pipeline().addLast(new DemoHandler2());
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) {
                                System.out.println(s);
                            }
                        });
                    }
                }).bind(8000);
    }
}
