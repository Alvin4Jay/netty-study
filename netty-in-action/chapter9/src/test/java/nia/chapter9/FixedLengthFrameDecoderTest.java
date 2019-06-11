package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * FixedLengthFrameDecoderTest 测试入栈handler
 *
 * @author xuanjian
 */
public class FixedLengthFrameDecoderTest {

    @Test
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer(); // 初始引用计数为1
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        assertTrue(channel.writeInbound(input.retain())); // retain()增加引用计数，变为2，数据从EmbeddedChannel写入后，buf/input引用计数-1
        assertTrue(channel.finish());

        ByteBuf read = channel.readInbound(); // 读取的消息引用计数为1
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound()); // null
        buf.release(); // release()后此处buf引用计数为0
    }

    @Test
    public void testFramesDecoded2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        assertFalse(channel.writeInbound(input.readBytes(2))); // false
        assertTrue(channel.writeInbound(input.readBytes(7))); // true

        assertTrue(channel.finish());

        ByteBuf read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound()); // null
        buf.release();
    }

}