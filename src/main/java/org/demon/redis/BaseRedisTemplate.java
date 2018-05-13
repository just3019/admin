package org.demon.redis;

import org.demon.util.Logger;
import org.demon.util.StringUtil;
import org.demon.util.ThreadUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.util.SafeEncoder;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Created by Sean.xie on 2017/2/28.
 */
public class BaseRedisTemplate<K, V> extends RedisTemplate<K, V> {
    private static final byte[] INCREMENT_KEY = SafeEncoder.encode("increment_key");
    private static final int RETRY_COUNT = 1;
    private static Long delta; // 时间差
    protected Logger logger = Logger.newInstance(getClass());

    /**
     * 获取redis 当前时间
     */
    public Long getCurrentTime() {
        return getCurrentTime(RETRY_COUNT);
    }

    /**
     * 获取当前时间
     */
    private Long getCurrentTime(int retry) {
        if (delta == null) {
            Long time = execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.time();
                }
            });
            if (time == null && retry <= RETRY_COUNT) {
                ThreadUtil.sleep(10);
                return getCurrentTime(++retry);
            } else {
                if (time == null) {
                    delta = null;
                } else {
                    delta = time - System.currentTimeMillis();
                }
                return time;
            }
        } else {
            return System.currentTimeMillis() + delta;
        }
    }

    /**
     * 获取时间(redis 获取不到,取本地时间)
     *
     * @return
     */
    public long getUnsafeCurrentTime() {
        Long time = getCurrentTime();
        if (time == null) {
            time = System.currentTimeMillis();
        }
        return time;
    }


    /**
     * 获取自增长当前值,全局唯一(redis服务器级别)
     *
     * @return
     */
    public long getIncrementValue() {
        return getIncrementValue(getCurrentTime(), 1);
    }

    /**
     * 获取自增长当前值,全局唯一(redis服务器级别)
     *
     * @param key key
     */
    public long getIncrementValue(String key) {
        if (StringUtil.isEmpty(key)) {
            throw new IllegalArgumentException("key need not empty");
        }
        return getIncrementValue(key.getBytes(), getCurrentTime(), 1, TimeUnit.DAYS.toSeconds(1));
    }

    /**
     * 获取递减值
     *
     * @param key key
     */
    public long getDecrementValue(String key) {
        if (StringUtil.isEmpty(key)) {
            throw new IllegalArgumentException("key need not empty");
        }
        return getIncrementValue(key.getBytes(), getCurrentTime(), -1, TimeUnit.DAYS.toSeconds(1));
    }

    /**
     * 获取自增长当前值,全局唯一(redis服务器级别)
     *
     * @param delta 变化量
     * @return
     */
    public long getIncrementValue(long delta) {
        return getIncrementValue(getCurrentTime(), delta);
    }

    /**
     * 获取自增长当前值,全局唯一(redis服务器级别)
     *
     * @param delta 变化量
     * @return
     */
    public long getIncrementValue(String key, long delta) {
        if (StringUtil.isEmpty(key)) {
            throw new IllegalArgumentException("key need not empty");
        }
        return getIncrementValue(key.getBytes(), getCurrentTime(), delta, TimeUnit.DAYS.toSeconds(1));
    }

    /**
     * 获取递减值
     *
     * @param delta 变化量
     * @return
     */
    public long getDecrementValue(String key, long delta) {
        if (StringUtil.isEmpty(key)) {
            throw new IllegalArgumentException("key need not empty");
        }
        return getIncrementValue(key.getBytes(), getCurrentTime(), 0 - delta, TimeUnit.DAYS.toSeconds(1));
    }

    /**
     * 获取自增长当前值,全局唯一(redis服务器级别)
     *
     * @param key     key
     * @param delta   变化量
     * @param timeout 超时时间 单位:秒
     * @return
     */
    public long getIncrementValue(String key, long delta, long timeout) {
        if (StringUtil.isEmpty(key)) {
            throw new IllegalArgumentException("key need not empty");
        }
        return getIncrementValue(key.getBytes(), getCurrentTime(), delta, TimeUnit.DAYS.toSeconds(timeout));
    }

    /**
     * 获取递减值
     *
     * @param key     key
     * @param delta   变化量
     * @param timeout 超时时间 单位:秒
     * @return
     */
    public long getDecrementValue(String key, long delta, long timeout) {
        if (StringUtil.isEmpty(key)) {
            throw new IllegalArgumentException("key need not empty");
        }
        return getIncrementValue(key.getBytes(), getCurrentTime(), 0 - delta, TimeUnit.DAYS.toSeconds(timeout));
    }

    /**
     * 获取自增长当前值
     *
     * @param initValue 默认值
     * @return
     */
    private long getIncrementValue(long initValue, long delta) {
        return getIncrementValue(INCREMENT_KEY, initValue, delta, 0);
    }

    /**
     * 获取自增长当前值
     *
     * @param key       key
     * @param initValue 初始值
     * @param delta     差值
     * @param timeout   超时时间
     */
    private long getIncrementValue(final byte[] key, long initValue, long delta, long timeout) {
        return execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Boolean result = connection.setNX(key, SafeEncoder.encode(String.valueOf(initValue)));
                Long value;
                if (Boolean.TRUE.equals(result)) {
                    value = initValue;
                } else {
                    if (delta == 1) {
                        value = connection.incr(key);
                    } else {
                        value = connection.incrBy(key, delta);
                    }
                }
                if (timeout > 0) {
                    connection.expire(key, timeout);
                }
                return value;
            }
        });
    }
}
