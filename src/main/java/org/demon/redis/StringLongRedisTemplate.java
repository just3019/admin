package org.demon.redis;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Description: 字符串 长整型 模板
 * Created by Sean.xie on 2016/12/14.
 */
public class StringLongRedisTemplate extends BaseRedisTemplate<String, Long> {

    /**
     * Constructs a new <code>StringLongRedisTemplate</code> instance. {@link #setConnectionFactory(RedisConnectionFactory)}
     * and {@link #afterPropertiesSet()} still need to be called.
     */
    public StringLongRedisTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer longSerializer = new JdkSerializationRedisSerializer();
        setKeySerializer(stringSerializer);
        setValueSerializer(longSerializer);
        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(longSerializer);
    }

    /**
     * Constructs a new <code>StringLongRedisTemplate</code> instance ready to be used.
     *
     * @param connectionFactory connection factory for creating new connections
     */
    public StringLongRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }

}