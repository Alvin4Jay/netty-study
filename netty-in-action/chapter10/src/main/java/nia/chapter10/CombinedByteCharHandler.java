package nia.chapter10;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * 10-10 CombinedChannelDuplexHandler的使用
 *
 * @author xuanjian
 */
public class CombinedByteCharHandler extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
    public CombinedByteCharHandler() {
        // 将委托实例传递给父类
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
