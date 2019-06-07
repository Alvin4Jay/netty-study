package nia.chapter5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ByteProcessor;
import org.junit.Assert;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

import static io.netty.channel.DummyChannelHandlerContext.DUMMY_INSTANCE;

/**
 * ByteBuf Example
 *
 * @author xuanjian
 */
public class ByteBufExample {

    private static final Random RANDOM = new Random();

    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    private static final ChannelHandlerContext CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE = DUMMY_INSTANCE;
    /**
     * 堆缓冲区
     */
    private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
    /**
     * 直接缓冲器
     */
    private static final ByteBuf DIRECT_BYTE_BUF_FROM_SOMEWHERE = Unpooled.directBuffer(1024);

    /**
     * Listing 5.1 Backing array
     */
    public static void heapBuffer() {
        ByteBuf heapBuf = BYTE_BUF_FROM_SOMEWHERE;

        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            // 当前ByteBuf第一个可读字节在backing array中的offset
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            handleArray(array, offset, length);
        }
    }

    /**
     * Listing 5.2 Direct buffer data access
     */
    public static void direstBuffer() {
        ByteBuf directBuf = DIRECT_BYTE_BUF_FROM_SOMEWHERE;

        if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            directBuf.getBytes(directBuf.readerIndex(), array);
            handleArray(array, 0, length);
        }
    }

    /**
     * Listing 5.3 Composite buffer pattern using ByteBuffer
     */
    public static void byteBufferComposite(ByteBuffer header, ByteBuffer body) {
        // Use an array to hold the message parts
        ByteBuffer[] message = new ByteBuffer[]{header, body};
        // 分配
        // Create a new ByteBuffer and use copy to merge the header and body
        ByteBuffer message2 = ByteBuffer.allocate(header.remaining() + body.remaining());
        // 数据拷贝
        message2.put(header);
        message2.put(body);
        // 写模式切换为读模式
        message2.flip();
    }

    /**
     * Listing 5.4 Composite buffer pattern using CompositeByteBuf
     */
    public static void byteBufComposite() {
        CompositeByteBuf messageByteBuf = Unpooled.compositeBuffer();
        ByteBuf header = BYTE_BUF_FROM_SOMEWHERE; // can be backing or direct
        ByteBuf body = BYTE_BUF_FROM_SOMEWHERE;
        messageByteBuf.addComponents(header, body);
        // ...
        messageByteBuf.removeComponent(0); // remove the header
        // 循环遍历所有的ByteBuf实例
        for (ByteBuf byteBuf : messageByteBuf) {
            System.out.println(byteBuf.toString());
        }
    }

    /**
     * Listing 5.5 Accessing the data in a CompositeByteBuf
     */
    public static void byteBufCompositeArray() {
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        int length = messageBuf.readableBytes();
        byte[] array = new byte[length];
        messageBuf.getBytes(messageBuf.readerIndex(), array);
        handleArray(array, 0, length);
    }

    /**
     * Listing 5.6 Access data
     */
    public static void byteBufAccess() {
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        for (int i = 0; i < byteBuf.capacity(); i++) {
            byte b = byteBuf.getByte(i);
            System.out.println((char) b);
        }
    }

    /**
     * Listing 5.7 Read all data
     */
    public static void readAllData() {
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        while (byteBuf.isReadable()) {
            System.out.println(byteBuf.readByte());
        }
    }

    /**
     * Listing 5.8 Write data
     */
    public static void write() {
        // Fills the writable bytes of a buffer with random integers.
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        while (byteBuf.writableBytes() >= 4) {
            byteBuf.writeByte(RANDOM.nextInt());
        }
    }

    /**
     * Listing 5.9 Using ByteProcessor to find \r
     * <p>
     * use {@link io.netty.buffer.ByteBufProcessor in Netty 4.0.x}
     */
    public static void byteProcessor() {
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        // 找到\r的位置
        int index = byteBuf.forEachByte(ByteProcessor.FIND_CR);
    }

    /**
     * Listing 5.9 Using ByteBufProcessor to find \r
     * <p>
     * use {@link io.netty.util.ByteProcessor in Netty 4.1.x}
     */
    public static void byteBufProcessor() {
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        int index = byteBuf.forEachByte(ByteBufProcessor.FIND_CR);
    }

    /**
     * Listing 5.10 Slice a ByteBuf
     */
    public static void byteBufSlice() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf sliced = byteBuf.slice(0, 15);
        System.out.println(sliced.toString(utf8));
        byteBuf.setByte(0, 'J');
        assert byteBuf.getByte(0) == sliced.getByte(0);
    }

    /**
     * Listing 5.11 Copying a ByteBuf
     */
    public static void byteBufCopy() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf copy = byteBuf.copy(0, 15);
        System.out.println(copy.toString(utf8));
        byteBuf.setByte(0, 'J');
        assert byteBuf.getByte(0) != copy.getByte(0);
    }

    /**
     * Listing 5.12 get() and set() usage
     */
    public static void byteBufSetGet() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char) byteBuf.getByte(0));
        int readIndex = byteBuf.readerIndex();
        int writeIndex = byteBuf.writerIndex();
        byteBuf.setByte(0, 'B');
        System.out.println((char) byteBuf.getByte(0));
        assert byteBuf.readerIndex() == readIndex;
        assert byteBuf.writerIndex() == writeIndex;
    }

    /**
     * Listing 5.13 read() and write() operations on the ByteBuf
     */
    public static void byteBufWriteRead() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char) byteBuf.readByte());
        int readIndex = byteBuf.readerIndex();
        int writeIndex = byteBuf.writerIndex();
        byteBuf.writeByte((byte) '?');
        Assert.assertEquals(byteBuf.readerIndex(), readIndex);
        Assert.assertNotEquals(byteBuf.writerIndex(), writeIndex);
    }

    private static void handleArray(byte[] array, int offset, int length) {
    }

    /**
     * Listing 5.14 Obtaining a ByteBufAllocator reference
     */
    public static void obtainingByteBufAllocatorReference() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ByteBufAllocator allocator = channel.alloc();

        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        ByteBufAllocator allocator2 = ctx.alloc();

    }

    /**
     * Listing 5.15 Reference counting
     */
    public static void referenceCounting() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ByteBufAllocator allocator = channel.alloc();

        ByteBuf buf = allocator.directBuffer();
        assert buf.refCnt() == 1;
    }

    /**
     * Listing 5.16 Release reference-counted object
     */
    public static void releaseReferenceCountedObject() {
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        boolean released = byteBuf.release();
    }

    public static void main(String[] args) {
        byteBufSlice();
        byteBufCopy();
        byteBufSetGet();
        byteBufWriteRead();
    }

}
