package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * FixedLengthFrameDecoder {@link ByteToMessageDecoder}
 *
 * @author xuanjian
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
    private int frameLength;

    public FixedLengthFrameDecoder(int frameLength) {
        if (frameLength <= 0) {
            throw new IllegalArgumentException("frameLength <= 0");
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= frameLength) {
            out.add(in.readBytes(frameLength));
        }
    }
}
