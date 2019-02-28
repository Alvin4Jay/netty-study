package netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import netty.protocol.Packet;
import netty.protocol.PacketCodec;

import java.util.List;

/**
 * Packet编解码器
 *
 * @author xuanjian.xuwj
 */
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {

    public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

    private PacketCodecHandler() {
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) {
        ByteBuf byteBuf = ctx.alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(byteBuf, msg);
        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        out.add(PacketCodec.INSTANCE.decode(msg));
    }
}
