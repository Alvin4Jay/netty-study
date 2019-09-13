package com.jay.wechat.server;

import com.jay.wechat.codec.PacketDecoder;
import com.jay.wechat.codec.PacketEncoder;
import com.jay.wechat.codec.Spliter;
import com.jay.wechat.server.handler.AuthHandler;
import com.jay.wechat.server.handler.CreateGroupRequestHandler;
import com.jay.wechat.server.handler.LoginRequestHandler;
import com.jay.wechat.server.handler.LogoutRequestHandler;
import com.jay.wechat.server.handler.MessageRequestHandler;
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
                        pipeline.addLast(new Spliter());
                        pipeline.addLast(new PacketDecoder());
                        pipeline.addLast(new LoginRequestHandler());
                        pipeline.addLast(new AuthHandler());
                        pipeline.addLast(new MessageRequestHandler());
                        pipeline.addLast(new CreateGroupRequestHandler());
                        pipeline.addLast(new LogoutRequestHandler());
                        pipeline.addLast(new PacketEncoder());
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
