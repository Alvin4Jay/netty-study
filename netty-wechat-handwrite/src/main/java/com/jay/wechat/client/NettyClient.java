package com.jay.wechat.client;

import com.jay.wechat.client.console.ConsoleCommandManager;
import com.jay.wechat.client.console.LoginConsoleCommand;
import com.jay.wechat.client.handler.CreateGroupResponseHandler;
import com.jay.wechat.client.handler.GroupMessageResponseHandler;
import com.jay.wechat.client.handler.HeartBeatTimerHandler;
import com.jay.wechat.client.handler.JoinGroupResponseHandler;
import com.jay.wechat.client.handler.ListGroupMembersResponseHandler;
import com.jay.wechat.client.handler.LoginResponseHandler;
import com.jay.wechat.client.handler.LogoutResponseHandler;
import com.jay.wechat.client.handler.MessageResponseHandler;
import com.jay.wechat.client.handler.QuitGroupResponseHandler;
import com.jay.wechat.codec.PacketDecoder;
import com.jay.wechat.codec.PacketEncoder;
import com.jay.wechat.codec.Spliter;
import com.jay.wechat.idle.IMIdleStateHandler;
import com.jay.wechat.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * NettyClient
 *
 * @author xuanjian
 */
public class NettyClient {

    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IMIdleStateHandler());
                        pipeline.addLast(new Spliter());
                        pipeline.addLast(new PacketDecoder());
                        pipeline.addLast(new LoginResponseHandler());
                        pipeline.addLast(new MessageResponseHandler());
                        pipeline.addLast(new CreateGroupResponseHandler());
                        pipeline.addLast(new QuitGroupResponseHandler());
                        pipeline.addLast(new JoinGroupResponseHandler());
                        pipeline.addLast(new ListGroupMembersResponseHandler());
                        pipeline.addLast(new GroupMessageResponseHandler());
                        pipeline.addLast(new LogoutResponseHandler());
                        pipeline.addLast(new PacketEncoder());
                        // 心跳定时器
                        pipeline.addLast(new HeartBeatTimerHandler());
                    }
                })
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);

        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }

    /**
     * 连接服务端(指数退避重连服务端)
     *
     * @param retry 还剩的重连次数
     */
    private static void connect(final Bootstrap bootstrap, String host, int port, final int retry) {
        bootstrap.connect(new InetSocketAddress(host, port)).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功，启动控制台线程");
                startConsoleThread(future.channel());
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接...");
            } else {
                // 第几次重连
                int order = MAX_RETRY - retry + 1;
                // 重连延时
                int delay = 1 << order;
                System.err.println(new Date() + ": 第" + order + "次重连...");
                bootstrap.config().group().schedule(() -> {
                    connect(bootstrap, host, port, retry - 1);
                }, delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(final Channel channel) {
        final Scanner in = new Scanner(System.in);
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();

        new Thread(() -> {
            while (!Thread.interrupted()) {
                // 判断是否已登录
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(in, channel);
                } else {
                    consoleCommandManager.exec(in, channel);
                }
            }
        }).start();
    }

}
