package org.demon.redis;

import org.demon.util.JSONType;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;

/**
 * Description: 集合类处理模板
 * Date: 2016/10/19 14:00
 *
 * @author Sean.xie
 */
public abstract class NameSpaceRedisTemplate<K, V> extends BaseRedisTemplate<String, Map<K, V>> {

    private final String namesplace = getNamesplace();

    /**
     * Constructs a new <code>NameSpaceRedisTemplate</code> instance. {@link #setConnectionFactory(RedisConnectionFactory)}
     * and {@link #afterPropertiesSet()} still need to be called.
     */
    private NameSpaceRedisTemplate(JSONType<V> type) {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        setKeySerializer(stringSerializer);
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        setValueSerializer(jdkSerializationRedisSerializer);
        setHashKeySerializer(jdkSerializationRedisSerializer);
        Gson2JsonRedisSerializer gson2JsonRedisSerializer = new Gson2JsonRedisSerializer(type.getType());
        setHashValueSerializer(gson2JsonRedisSerializer);
    }

    /**
     * Constructs a new <code>StringRedisTemplate</code> instance ready to be used.
     *
     * @param connectionFactory connection factory for creating new connections
     */
    public NameSpaceRedisTemplate(RedisConnectionFactory connectionFactory, JSONType<V> type) {
        this(type);
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }

    protected abstract String getNamesplace();

    /**
     * 取命名空间下所有值
     *
     * @return
     */
    public Map<K, V> getNamesplaceAll() {
        return boundValueOps(namesplace).get();
    }

    /**
     * 取命名空间下的key对应的value
     *
     * @param key
     * @return
     */
    public V getNamesplaceValue(K key) {
        BoundHashOperations<String, K, V> boundHashOperations = boundHashOps(namesplace);
        return boundHashOperations.get(key);
    }

    /**
     * 存放的命名空间下
     *
     * @param values
     */
    public void putNamesplaceValue(Map<K, V> values) {
        BoundHashOperations<String, K, V> boundHashOperations = boundHashOps(namesplace);
        boundHashOperations.putAll(values);
    }

    /**
     * 存放的命名空间下
     *
     * @param key
     * @param value
     */
    public void putNamesplaceValue(K key, V value) {
        BoundHashOperations<String, K, V> boundHashOperations = boundHashOps(namesplace);
        boundHashOperations.put(key, value);
    }

    /**
     * 删除命名空间下所有值
     */
    public void deleteNamesplace() {
        delete(namesplace);
    }

    /**
     * 删除指定key
     *
     * @param keys
     */
    public void deleteByKey(K... keys) {
        BoundHashOperations<String, K, V> boundHashOperations = boundHashOps(namesplace);
        boundHashOperations.delete(keys);
    }

    public long size() {
        BoundHashOperations<String, K, V> boundHashOperations = boundHashOps(namesplace);
        Long size = boundHashOperations.size();
        return size == null ? 0 : size;
    }

}
