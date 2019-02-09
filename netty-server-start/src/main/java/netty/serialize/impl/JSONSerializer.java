package netty.serialize.impl;

import com.alibaba.fastjson.JSON;

import netty.serialize.Serializer;
import netty.serialize.SerializerAlgorithm;

/**
 * JSONSerializer
 *
 * @author xuanjian.xuwj
 */
public class JSONSerializer implements Serializer {
    @Override
    public Byte getSerializeAlgorithm() {
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
