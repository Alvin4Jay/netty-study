package com.jay.wechat.server;

import com.jay.wechat.codec.PacketCodecHandler;
import com.jay.wechat.codec.Spliter;
import com.jay.wechat.idle.IMIdleStateHandler;
import com.jay.wechat.server.handler.AuthHandler;
import com.jay.wechat.server.handler.HeartBeatRequestHandler;
import com.jay.wechat.server.handler.IMHandler;
import com.jay.wechat.server.handler.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * NettyServer
 *
 * @author xuanjian
 */
public class NettyServer {

    public static void main(String[] args) {

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IMIdleStateHandler());
                        pipeline.addLast(new Spliter());
                        pipeline.addLast(PacketCodecHandler.INSTANCE);
                        pipeline.addLast(HeartBeatRequestHandler.INSTANCE);
                        pipeline.addLast(LoginRequestHandler.INSTANCE);
                        pipeline.addLast(AuthHandler.INSTANCE);
                        pipeline.addLast(IMHandler.INSTANCE);
                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true);

        bind(serverBootstrap, 8000);
    }

    /**
     * 自动绑定递增端口
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(8000).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("端口绑定[" + port + "]成功");
            } else {
                System.out.println("端口绑定[" + port + "]失败");
                bind(serverBootstrap, port + 1);
            }
        });
    }

}
