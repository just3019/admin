package org.demon.config;

import org.demon.redis.StringLongRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Description: Redis 配置项
 * Date: 2016/11/15 18:44
 *
 * @author Sean.xie
 */
@Configuration
@ConditionalOnClass(Jedis.class)
public class RedisConfig {

    @Value("${spring.redis.host}")
    public String host;
    @Value("${spring.redis.database}")
    public int database = 0;
    @Value("${spring.redis.port}")
    public int port;
    @Value("${spring.redis.password}")
    public String pass;
    @Value("${spring.redis.timeout}")
    public int timeout = 30000;
    @Value("${spring.redis.pool.min-idle}")
    public int minIdle;
    @Value("${spring.redis.pool.max-idle}")
    public int maxIdle;
    @Value("${spring.redis.pool.max-active}")
    public int maxTotal;
    @Value("${spring.redis.pool.max-wait}")
    public int maxWaitMillis;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }

    @Bean
    @Autowired
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setPassword(pass);
        factory.setDatabase(database);
        factory.setPoolConfig(jedisPoolConfig);
        return factory;
    }

    @Bean
    @Autowired
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @Autowired
    public StringLongRedisTemplate stringLongRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringLongRedisTemplate(redisConnectionFactory);
    }
}
