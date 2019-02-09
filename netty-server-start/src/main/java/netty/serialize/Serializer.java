package netty.serialize;

import netty.serialize.impl.JSONSerializer;

/**
 * Serializer
 *
 * @author xuanjian.xuwj
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 获取序列化算法
     */
    Byte getSerializeAlgorithm();

    /**
     * 对象序列化
     */
    byte[] serialize(Object object);

    /**
     * 对象反序列化
     */
    <T> T deserialize(Class<T> clazz, byte[] data);

}
