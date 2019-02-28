package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.util.SessionUtil;

import java.util.Date;

/**
 * 客户端身份校验 登录状态
 *
 * @author xuanjian.xuwj
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {
    }

    //    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + ": AuthHandler --> handlerAdded");
//        super.handlerAdded(ctx);
//    }
//
//    @Override
//    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + ": AuthHandler --> channelRegistered");
//        super.channelRegistered(ctx);
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + ": AuthHandler --> channelActive");
//        super.channelActive(ctx);
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println(new Date() + ": AuthHandler --> channelRead");
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            // 已经登录，则移除自己
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + ": AuthHandler --> channelReadComplete");
//        super.channelReadComplete(ctx);
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + ": AuthHandler --> channelInactive");
//        super.channelInactive(ctx);
//    }
//
//    @Override
//    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + ": AuthHandler --> channelUnregistered");
//        super.channelUnregistered(ctx);
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + ": AuthHandler --> handlerRemoved");
//        if (LoginUtil.hasLogin(ctx.channel())) {
//            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
//        } else {
//            System.out.println("无登录验证，强制关闭连接!");
//        }
//    }
}
