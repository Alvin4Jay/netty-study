package com.jay.wechat.client;

import com.jay.wechat.client.handler.LoginResponseHandler;
import com.jay.wechat.client.handler.MessageResponseHandler;
import com.jay.wechat.codec.PacketDecoder;
import com.jay.wechat.codec.PacketEncoder;
import com.jay.wechat.codec.Spliter;
import com.jay.wechat.protocol.request.MessageRequestPacket;
import com.jay.wechat.util.LoginUtil;
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
                        pipeline.addLast(new Spliter());
                        pipeline.addLast(new PacketDecoder());
                        pipeline.addLast(new LoginResponseHandler());
                        pipeline.addLast(new MessageResponseHandler());
                        pipeline.addLast(new PacketEncoder());
                    }
                })
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

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                // 判断是否已登录
                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送至服务端: ");

                    Scanner in = new Scanner(System.in);
                    String message = in.nextLine();

                    MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                    messageRequestPacket.setMessage(message);

                    channel.writeAndFlush(messageRequestPacket);
                }
            }
        }).start();
    }

}
