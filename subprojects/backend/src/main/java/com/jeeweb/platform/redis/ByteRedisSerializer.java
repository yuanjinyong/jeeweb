/**
 * 
 */
package com.jeeweb.platform.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.jeeweb.framework.core.utils.SerializerUtil;

/**
 * @author 袁进勇
 *
 */
public class ByteRedisSerializer<T> implements RedisSerializer<T> {
    @Override
    public byte[] serialize(T object) throws SerializationException {
        return SerializerUtil.serialize(object);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return (T) SerializerUtil.deserialize(bytes);
    }
}
