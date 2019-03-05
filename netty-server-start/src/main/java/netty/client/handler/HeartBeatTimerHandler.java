package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.protocol.request.HeartBeatRequestPacket;

import java.util.concurrent.TimeUnit;

/**
 * 客户端定时向服务端发送心跳
 *
 * @author xuanjian.xuwj
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEART_BEAT_INTERNAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);

        super.channelActive(ctx);
    }

    /**
     * 每隔HEART_BEAT_INTERNAL，发送心跳包
     *
     * @param ctx
     */
    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }
        }, HEART_BEAT_INTERNAL, TimeUnit.SECONDS);
    }
}
