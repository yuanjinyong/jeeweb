/**
 * 
 */
package com.jeeweb.platform.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.framework.core.utils.SerializeUtil;

import redis.clients.jedis.Jedis;

/**
 * @author 袁进勇
 *
 */
@Component
public class RedisService implements InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(RedisService.class);
    //
    // @Autowired
    // private RedisProperties properties;

    @Value("${redis.host:127.0.0.1}")
    private String host;

    @Value("${redis.port:6379}")
    private Integer port;

    // @Value("${redis.password:#{null}}")
    @Value("${redis.password:zhukuredis@123321}")
    private String password;

    private Jedis jedis;

    /**
     * 根据key获取字符串格式的内容
     * 
     * @param key
     * @return
     */
    public String get(String key) {
        try {
            return jedis.get(key);
        } finally {
            jedis.close();
        }
    }

    /**
     * 根据key获取对象格式的内容
     * 
     * @param key
     * @param clazz
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        // return JsonUtil.toObject(get(key), clazz);
        try {
            return SerializeUtil.unserialize(jedis.get(key.getBytes()), clazz);
        } finally {
            jedis.close();
        }
    }

    /**
     * 向缓存中设置对象
     * 
     * @param key
     * @param value
     * @return
     */
    public void set(String key, Object value) {
        set(key, value, 0);
    }

    /**
     * 向缓存中设置对象
     * 
     * @param key
     * @param value
     * @param expireSeconds
     *            如果小于等于0，则永不过期
     */
    public void set(String key, Object value, int expireSeconds) {
        try {
            if (expireSeconds > 0) {
                if (value instanceof String) {
                    jedis.setex(key, expireSeconds, (String) value);
                } else {
                    // jedis.setex(key, expireSeconds, JsonUtil.toString(value));
                    jedis.setex(key.getBytes(), expireSeconds, SerializeUtil.serialize(value));
                }
            } else {
                if (value instanceof String) {
                    jedis.set(key, (String) value);
                } else {
                    // jedis.set(key, JsonUtil.toString(value));
                    jedis.set(key.getBytes(), SerializeUtil.serialize(value));
                }
            }
        } finally {
            jedis.close();
        }
    }

    /**
     * 删除缓存中得对象，根据key
     * 
     * @param key
     * @return
     */
    public void del(String key) {
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (HelpUtil.isEmpty(password)) {
            String errorMsg = "未检测到连接Redis服务器的密码，请先在classpath:cfg/application.properties文件中添加“redis.password=实际的密码”。";
            LOG.error(errorMsg);
            throw new BusinessException(errorMsg);
        }

        jedis = new Jedis(host, port);
        jedis.auth(password);
    }
}
