package netty.protocol.command;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * Packet
 *
 * @author xuanjian.xuwj
 */
@Data
public abstract class Packet {

    /**
     * 通讯协议版本
     */
    @JSONField(serialize = false, deserialize = false)
    private Byte version = 1;

    /**
     * 获取包对应的指令
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
