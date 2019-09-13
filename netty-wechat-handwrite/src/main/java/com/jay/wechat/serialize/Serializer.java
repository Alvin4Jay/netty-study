package com.jay.wechat.serialize;

import com.jay.wechat.serialize.impl.JSONSerializer;

/**
 * Serializer
 *
 * @author xuanjian
 */
public interface Serializer {

    Serializer DEFAULT = JSONSerializer.INSTANCE;

    /**
     * 序列化算法类型
     *
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * Java对象转为二进制
     *
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制转为Java对象
     *
     * @param clazz
     * @param data
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] data);

}
