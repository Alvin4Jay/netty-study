package nia.chapter5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

/**
 * ByteBuf Example
 *
 * @author xuanjian
 */
public class ByteBufExample {

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

    private static void handleArray(byte[] array, int offset, int length) {
    }

    public static void main(String[] args) {

    }

}
