package netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import netty.client.console.ConsoleCommandManager;
import netty.client.console.LoginConsoleCommand;
import netty.client.handler.CreateGroupResponseHandler;
import netty.client.handler.LoginResponseHandler;
import netty.client.handler.LogoutResponseHandler;
import netty.client.handler.MessageResponseHandler;
import netty.codec.PacketDecoder;
import netty.codec.PacketEncoder;
import netty.codec.Spliter;
import netty.util.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Netty Client Start
 *
 * @author xuanjian.xuwj
 */
public class NettyClient {

    /**
     * 最大重连次数
     */
    private static final int MAX_RETRY = 5;
    private static final AttributeKey<String> CLIENT_KEY = AttributeKey.newInstance("clientKey");
    private static final String CLIENT_VALUE = "clientValue";

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        // System.out.println("clientKey: " + ch.attr(CLIENT_KEY).get());

                        // ch.pipeline().addLast(new FirstClientHandler());
                        // ch.pipeline().addLast(new ClientHandler());

                        // 粘包问题
                        // ch.pipeline().addLast(new FirstClientHandlerForStickyBagDemo());
                        // 客户端的pipeline
                        ch.pipeline().addLast(new Spliter()); // 拆包器
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                })
                .option(ChannelOption.SO_KEEPALIVE, true)
                // 超过这个时间还是建立不上的话，则代表连接失败，需要重连
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.TCP_NODELAY, true)
                // 给客户端NioSocketChannel设置属性
                .attr(CLIENT_KEY, CLIENT_VALUE);

        // 4.建立连接
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }

    /**
     * 自动重连
     *
     * @param bootstrap 引导类
     * @param host      主机名
     * @param port      端口
     * @param retry     剩余重试次数
     */
    private static void connect(final Bootstrap bootstrap, String host, int port, final int retry) {
        bootstrap.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println(new Date() + ": 连接成功，启动控制台线程!");
                    Channel channel = ((ChannelFuture) future).channel();
                    startConsoleThread(channel);
                } else if (retry == 0) {
                    System.err.println(new Date() + ": 重试次数已用完，放弃连接!");
                } else {
                    // 第几次重连
                    int order = MAX_RETRY - retry + 1;
                    // 本次重连间隔(s)
                    int delay = 1 << order;
                    System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                    // EventLoopGroup.schedule()
                    bootstrap.config().group().schedule(() -> {
                        connect(bootstrap, host, port, retry - 1);
                    }, delay, TimeUnit.SECONDS);
                }
            }
        });
    }

    /**
     * 启动控制台线程，监听客户端控制台输入消息
     *
     * @param channel 客户端Channel
     */
    private static void startConsoleThread(Channel channel) {
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    // 未登录，直接执行登录命令
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    // 已登录，执行其他命令
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }
}
