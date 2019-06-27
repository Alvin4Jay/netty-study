package nia.chapter11;

import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * 11-14 使用 ProtoBuf
 *
 * @author xuanjian
 */
public class ProtobufInitializer extends ChannelInitializer<Channel> {

    private final MessageLite messageLite;

    public ProtobufInitializer(MessageLite messageLite) {
        this.messageLite = messageLite;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 分隔帧
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        // 添加帧长度
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        // 编码
        pipeline.addLast(new ProtobufEncoder());
        // 解码
        pipeline.addLast(new ProtobufDecoder(messageLite));
        // 解码后的对象处理
        pipeline.addLast(new ObjectHandler());
    }

    public static final class ObjectHandler extends SimpleChannelInboundHandler<Object> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            // do something with the object
        }
    }
}
