package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import netty.codec.PacketDecoder;
import netty.codec.PacketEncoder;
import netty.codec.Spliter;
import netty.server.handler.AuthHandler;
import netty.server.handler.LoginRequestHandler;
import netty.server.handler.MessageRequestHandler;

import java.util.Date;

/**
 * Netty Server Start
 *
 * @author xuanjian.xuwj
 */
public class NettyServer {

    private static final int BEGIN_PORT = 8000;
    private static final AttributeKey<String> SERVER_NAME_KEY = AttributeKey.newInstance("serverName");
    private static final String SERVER_NAME_VALUE = "NettyServer";
    private static final AttributeKey<String> CLIENT_KEY = AttributeKey.newInstance("clientKey");
    private static final String CLIENT_VALUE = "clientValue";

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap
                // 指定线程模型 boss worker
                .group(bossGroup, workerGroup)
                // IO模型为NIO
                .channel(NioServerSocketChannel.class)
                // 指定连接数据读写处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        // System.out.println("clientKey: " + ch.attr(CLIENT_KEY).get());

                        // ch.pipeline().addLast(new FirstServerHandler());
                        // ch.pipeline().addLast(new ServerHandler());
                        // 入站事件处理，处理读数据的逻辑链
                        // ch.pipeline().addLast(new InBoundHandlerA());
                        // ch.pipeline().addLast(new InBoundHandlerB());
                        // ch.pipeline().addLast(new InBoundHandlerC());

                        // 出站事件处理，处理写数据的逻辑链
                        // ch.pipeline().addLast(new OutBoundHandlerA());
                        // ch.pipeline().addLast(new OutBoundHandlerB());
                        // ch.pipeline().addLast(new OutBoundHandlerC());

                        // 粘包问题
                        // ch.pipeline().addLast(new FirstServerHandlerForStickyBagDemo());
                        // 服务端pipeline
                        // ch.pipeline().addLast(new ConnectionStatHandler()); // 统计客户端连接数
                        // ch.pipeline().addLast(new InboundTrafficStatHandler()); // 入口流量统计
                        // ch.pipeline().addLast(new LifeCycleTestHandler()); // ChannelHandler生命周期测试
                        ch.pipeline().addLast(new Spliter()); // 拆包器
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new AuthHandler()); // 身份验证
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                })
                // 给每一条连接指定自定义属性
                .childAttr(CLIENT_KEY, CLIENT_VALUE)
                // 给每条连接设置一些TCP底层相关的属性
                .childOption(ChannelOption.SO_KEEPALIVE, true) // TCP心跳开启
                .childOption(ChannelOption.TCP_NODELAY, true) // 数据实时性高
                // 给服务端channel设置一些TCP属性
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 给服务端Channel指定属性
                .attr(SERVER_NAME_KEY, SERVER_NAME_VALUE)
                // 指定在服务端启动过程中的一些逻辑
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        // System.out.println("服务端启动中..." + ch.attr(SERVER_NAME_KEY).get());
                    }
                });
        bind(serverBootstrap, BEGIN_PORT);
    }

    /**
     * 自动绑定递增端口
     *
     * @param serverBootstrap 服务端引导类
     * @param port            绑定端口
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
                    // 定时打印客户端连接数
//                    serverBootstrap.config().group().scheduleAtFixedRate(() -> {
//                        System.out.println(new Date() + ": client connections count --> " + ConnectionStatHandler.CONNECTIONS);
//                    }, 1, 1, TimeUnit.SECONDS);
                } else {
                    System.err.println(new Date() + ": 端口[" + port + "]绑定失败!");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
