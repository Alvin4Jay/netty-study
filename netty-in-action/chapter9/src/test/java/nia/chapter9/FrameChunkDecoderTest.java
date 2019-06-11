package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * FrameChunkDecoderTest
 *
 * @author xuanjian
 */
public class FrameChunkDecoderTest {

    @Test
    public void testFrameDecoded() {
        ByteBuf byteBuf = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            byteBuf.writeByte(i);
        }
        ByteBuf input = byteBuf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));

        assertTrue(channel.writeInbound(input.readBytes(2)));

        try {
            channel.writeInbound(input.readBytes(4));
            Assert.fail(); // 到这里，测试失败
        } catch (TooLongFrameException e) {
            // expected exception
            System.out.println("error");
        }

        assertTrue(channel.writeInbound(input.readBytes(3)));
        assertTrue(channel.finish());

        ByteBuf read = channel.readInbound();
        assertEquals(byteBuf.readSlice(2), read);
        read.release();

        read = channel.readInbound();
        assertEquals(byteBuf.skipBytes(4).readSlice(3), read);
        read.release();

        byteBuf.release();
    }

}