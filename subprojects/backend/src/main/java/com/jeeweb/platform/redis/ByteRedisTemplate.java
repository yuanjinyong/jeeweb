/**
 * 
 */
package com.jeeweb.platform.redis;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 袁进勇
 *
 */
public class ByteRedisTemplate<V> extends RedisTemplate<String, V> {

    public ByteRedisTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        setKeySerializer(stringSerializer);

        ByteRedisSerializer<V> jsonSerializer = new ByteRedisSerializer<V>();
        setValueSerializer(jsonSerializer);

        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(stringSerializer);
    }

    public ByteRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
