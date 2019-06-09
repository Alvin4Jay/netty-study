package nia.chapter8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.InetSocketAddress;

/**
 * 引导和使用ChannelInitializer 8-6
 *
 * @author xuanjian
 */
public class BootstrapWithInitializer {
    public static void main(String[] args) {
        BootstrapWithInitializer bootstrap = new BootstrapWithInitializer();
        bootstrap.bootstrap();
    }

    private void bootstrap() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializerImpl());

        ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(8080));

        future.syncUninterruptibly();
    }

    /**
     * 初始化handler
     */
    final class ChannelInitializerImpl extends ChannelInitializer<Channel> {
        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(new HttpClientCodec());
            ch.pipeline().addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        }
    }
}
