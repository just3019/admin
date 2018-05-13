package org.demon.redis;

import org.demon.util.JSONUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Description: 使用Gson处理的 Redis编码类
 * Date: 2016/11/1 17:50
 *
 * @author Sean.xie
 */
public class Gson2JsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private final Type javaType;


    /**
     * Creates a new {@link Gson2JsonRedisSerializer} for the given target {@link org.demon.util.JSONType}.
     */
    public Gson2JsonRedisSerializer(Type type) {
        this.javaType = type;
    }


    /**
     * JSON 解码
     */
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return JSONUtil.json2Obj(new String(bytes), javaType);
        } catch (Exception ex) {
            throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    /**
     * JSON 编码
     */
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        try {
            return JSONUtil.obj2Json(t).getBytes();
        } catch (Exception ex) {
            throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

}
