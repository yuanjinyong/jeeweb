/**
 * 
 */
package com.jeeweb.framework.redis;

import java.lang.reflect.ParameterizedType;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 袁进勇
 *
 */
public class JsonRedisTemplate<V> extends RedisTemplate<String, V> {

    public JsonRedisTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        setKeySerializer(stringSerializer);

        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked")
        Class<V> clz = (Class<V>) parameterizedType.getActualTypeArguments()[0];
        Jackson2JsonRedisSerializer<V> jsonSerializer = new Jackson2JsonRedisSerializer<V>(clz);
        setValueSerializer(jsonSerializer);

        ObjectMapper om = new ObjectMapper();
        jsonSerializer.setObjectMapper(om);
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(stringSerializer);
    }

    public JsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
