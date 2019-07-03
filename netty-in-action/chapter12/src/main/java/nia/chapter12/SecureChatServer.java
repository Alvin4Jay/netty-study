package nia.chapter12;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.net.InetSocketAddress;

/**
 * 添加SSL支持的聊天服务器
 *
 * @author xuanjian
 */
public class SecureChatServer extends ChatServer {

    private final SslContext context;

    public SecureChatServer(SslContext context) {
        this.context = context;
    }

    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new SecureChatServerInitializer(group, context);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Please give port as argument");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        // ssl支持
        SelfSignedCertificate certificate = new SelfSignedCertificate();
        SslContext context = SslContext.newServerContext(certificate.certificate(), certificate.privateKey());
        final SecureChatServer server = new SecureChatServer(context);
        ChannelFuture future = server.start(new InetSocketAddress(port));

        Runtime.getRuntime().addShutdownHook(new Thread(server::destroy));

        future.channel().closeFuture().syncUninterruptibly();
    }

}
