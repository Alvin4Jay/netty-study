package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * 使用属性值 8-7
 *
 * @author xuanjian
 */
public class BootstrapClientWithOptionsAndAttrs {
    public static void main(String[] args) {
        BootstrapClientWithOptionsAndAttrs boot = new BootstrapClientWithOptionsAndAttrs();
        boot.bootstrap();
    }

    private void bootstrap() {
        final AttributeKey<Integer> id = AttributeKey.valueOf("id");

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        Integer idValue = ctx.channel().attr(id).get();
                        // do something with the idValue
                        System.out.println("idValue: " + idValue);
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received Data");
                    }
                });
        // ChannelOption
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        // Attribute
        bootstrap.attr(id, 123456);

        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
        future.syncUninterruptibly();
    }
}
