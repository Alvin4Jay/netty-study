package com.jay.wechat.serialize.impl;

import com.alibaba.fastjson.JSON;

import com.jay.wechat.serialize.Serializer;
import com.jay.wechat.serialize.SerializerAlgorithm;

/**
 * JSONSerializer
 *
 * @author xuanjian
 */
public class JSONSerializer implements Serializer {

    public static final JSONSerializer INSTANCE = new JSONSerializer();

    private JSONSerializer() {
    }

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] data) {
        return JSON.parseObject(data, clazz);
    }
}
