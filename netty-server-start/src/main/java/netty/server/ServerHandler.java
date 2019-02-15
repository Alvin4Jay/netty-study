package netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.protocol.Packet;
import netty.protocol.PacketCodec;
import netty.protocol.request.LoginRequestPacket;
import netty.protocol.request.MessageRequestPacket;
import netty.protocol.response.LoginResponsePacket;
import netty.protocol.response.MessageResponsePacket;

import java.util.Date;

/**
 * Server ChannelInBoundHandler
 *
 * @author xuanjian.xuwj
 */
@Deprecated
public class ServerHandler extends ChannelInboundHandlerAdapter {
    // 读取数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf request = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodec.INSTANCE.decode(request);

        // TODO 此处 if else 可用 策略模式 重构, Map结构
        if (packet instanceof LoginRequestPacket) {
            System.out.println(new Date() + ": 收到客户端登录请求......");
            // 登录校验
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(loginRequestPacket.getVersion());
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ": 客户端登录成功......");
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号密码校验失败");
                System.out.println(new Date() + ": 客户端登陆失败......");
            }

            // 登录响应
            ByteBuf response = PacketCodec.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(response);
        } else if (packet instanceof MessageRequestPacket) {
            // 处理消息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复: [" + messageRequestPacket.getMessage() + "]");
            ByteBuf responseMessage = PacketCodec.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseMessage);
        }

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
