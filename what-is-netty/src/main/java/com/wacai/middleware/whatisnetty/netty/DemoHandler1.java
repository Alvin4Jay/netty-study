package com.wacai.middleware.whatisnetty.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * DemoHandler1
 *
 * @author xuanjian.xuwj
 */
public class DemoHandler1 extends ChannelInboundHandlerAdapter {
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded()--->DemoHandler1");
        System.err.println("channel registered test1: " + ctx.channel().isRegistered());
        System.out.println("channel: " + ctx.channel().toString());
        System.out.println("channel pipeline: " + ctx.channel().pipeline().toString());
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered()--->DemoHandler1");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive()--->DemoHandler1");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead()--->DemoHandler1");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete()--->DemoHandler1");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive()--->DemoHandler1");
        // true
        System.out.println("channel channelInactive register:()--->DemoHandler1: " + ctx.channel().isRegistered());
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered()--->DemoHandler1");
        // false
        System.out.println("channel channelUnregistered register:()--->DemoHandler1: " + ctx.channel().isRegistered());
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved()--->DemoHandler1");
        super.handlerRemoved(ctx);
    }
}
