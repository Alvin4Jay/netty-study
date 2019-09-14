package com.jay.wechat.client.handler;

import com.jay.wechat.protocol.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * HeartBeatTimerHandler 定时发送心跳包
 *
 * @author xuanjian
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEART_BEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
        ctx.fireChannelActive();
    }

    private void scheduleSendHeartBeat(final ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                ctx.channel().writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }
        }, HEART_BEAT_INTERVAL, TimeUnit.SECONDS);
    }

}
