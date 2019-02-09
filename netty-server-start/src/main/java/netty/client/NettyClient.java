package netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
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

                        ch.pipeline().addLast(new FirstClientHandler());
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
                    System.out.println("连接成功!");
                } else if (retry == 0) {
                    System.err.println("重试次数已用完，放弃连接!");
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
}
