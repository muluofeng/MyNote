package com.example.netty.packet;

/**
 * @author xiexingxing
 * @Created by 2020-04-16 10:47.
 */
public interface Serializer {

    /**
     * json 序列化
     */
    byte JSON_SERIALIZER = SerializerAlgorithm.JSON;

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}