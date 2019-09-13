package com.jay.wechat.protocol;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * Abstract Packet
 *
 * @author xuanjian
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    @JSONField(serialize = false, deserialize = false)
    private byte version = 1;

    public abstract byte getCommand();

}
