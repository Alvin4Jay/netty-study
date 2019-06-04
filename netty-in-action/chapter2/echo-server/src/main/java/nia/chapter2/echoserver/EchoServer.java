package nia.chapter2.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Echo Server
 *
 * @author xuanjian
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println(
                    "Usage: " + EchoServer.class.getSimpleName() + " <port>");
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    private void start() throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            serverBootstrap
                    .group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler(countDownLatch));
                        }
                    });

            ChannelFuture future = serverBootstrap.bind().sync();
            System.out.println(EchoServer.class.getSimpleName() +
                    " started and listening for connections on " + future.channel().localAddress());

            // 关闭Server
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    future.channel().close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();


            future.channel().closeFuture().sync();
            System.out.println(new Date() + " Server end....");

        } finally {
            group.shutdownGracefully().sync();
        }

    }
}
