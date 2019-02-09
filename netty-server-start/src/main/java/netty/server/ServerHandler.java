package netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.protocol.Packet;
import netty.protocol.PacketCodec;
import netty.protocol.request.LoginRequestPacket;
import netty.protocol.response.LoginResponsePacket;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Server ChannelInBoundHandler
 *
 * @author xuanjian.xuwj
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    // 读取数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录......");
        ByteBuf request = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodec.INSTANCE.decode(request);

        if (packet instanceof LoginRequestPacket) {
            // 登录校验
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(loginRequestPacket.getVersion());
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ": 客户端登陆成功......");
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号密码校验失败");
                System.out.println(new Date() + ": 客户端登陆失败......");
            }

            // 登录响应
            ByteBuf response = PacketCodec.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(response);
        }

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
