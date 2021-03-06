package nia.chapter6;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DummyChannelPipeline;
import io.netty.util.CharsetUtil;

import static io.netty.channel.DummyChannelHandlerContext.DUMMY_INSTANCE;

/**
 * 写出数据
 *
 * @author xuanjian
 */
public class WriteHandlers {

    private static final ChannelHandlerContext CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE = DUMMY_INSTANCE;

    private static final ChannelPipeline CHANNEL_PIPELINE_FROM_SOMEWHERE = DummyChannelPipeline.DUMMY_INSTANCE;

    /**
     * 从ChannelHandlerContext访问Channel 6-6
     */
    public static void writeViaChannel() {
        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        Channel channel = ctx.channel();
        channel.write(Unpooled.copiedBuffer("Netty in action", CharsetUtil.UTF_8));
    }

    /**
     * 从ChannelHandlerContext访问ChannelPipeline 6-7
     */
    public static void writeViaChannelPipeline() {
        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.write(Unpooled.copiedBuffer("Netty in action", CharsetUtil.UTF_8));
    }

    /**
     * 调用ChannelHandlerContext的write方法 6-8
     */
    public static void writeViaChannelHandlerContext() {
        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        ctx.write(Unpooled.copiedBuffer("Netty in action", CharsetUtil.UTF_8));
    }

}
