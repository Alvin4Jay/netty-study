package netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.protocol.Packet;
import netty.protocol.request.LoginRequestPacket;
import netty.protocol.PacketCodec;
import netty.protocol.response.LoginResponsePacket;

import java.util.Date;
import java.util.UUID;

/**
 * ClientHandler
 *
 * @author xuanjian.xuwj
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端开始登录...");

        // 1. 构建登录请求数据包
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("netty-study");
        loginRequestPacket.setPassword("pwd");

        // 2. 编码
        ByteBuf request = PacketCodec.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        // 3.写数据到服务端
        ctx.channel().writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            // 登录响应
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + ": 客户端登录成功...");
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因是: " + loginResponsePacket.getReason());
            }
        }

    }
}
