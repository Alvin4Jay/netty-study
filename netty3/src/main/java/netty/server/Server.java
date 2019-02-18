package netty.server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * 服务端引导类
 *
 * @author xuanjian.xuwj
 */
public class Server {
    public static void main(String[] args) {
        ChannelFactory channelFactory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), // boss线程池
                Executors.newCachedThreadPool(), // worker线程池
                8 // worker线程数
        );

        ServerBootstrap serverBootstrap = new ServerBootstrap(channelFactory);

        // 对于每一个连接channel, server都会调用PipelineFactory为该连接创建一个ChannelPipline
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline channelPipeline = Channels.pipeline();
                channelPipeline.addLast("decoder", new StringDecoder());
                channelPipeline.addLast("serverHandler", new ServerHandler());
                channelPipeline.addLast("encoder", new StringEncoder());
                return channelPipeline;
            }
        });

        serverBootstrap.bind(new InetSocketAddress("127.0.0.1", 8080));
        System.out.println("服务端启动成功...");
    }
}
