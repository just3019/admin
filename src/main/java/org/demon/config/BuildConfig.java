package org.demon.config;

import org.demon.request.JWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Description: 应用通用配置
 * Date: 2016/11/17 15:53
 *
 * @author Sean.xie
 */
@Component
@Configuration
public class BuildConfig {
    //JWT 秘钥
    @Value("${build.jwt.key}")
    private String key;

    // Debug 开关
    @Value("${build.debug.enable}")
    private boolean debug;
    // 是否添加Gson Converter
    @Value("${build.addConverter}")
    private boolean addConverter = true;

    @Value("${build.shouldNullEnum}")
    private boolean shouldNullEnum = false;
    //同步任务
    @Value("${build.syncTask.enable}")
    private boolean syncTaskEnable;
    //同步任务超时时间
    @Value("${build.syncTask.timeout}")
    private long syncTaskTimeout;
    //删除Key
    @Value("${build.syncTask.deleteKey}")
    private boolean syncTaskDeleteKey;

    public boolean isDebug() {
        return debug;
    }

    public String getJWTKey() {
        return key;
    }

    public boolean isAddConverter() {
        return addConverter;
    }

    public boolean shouldNullEnum() {
        return shouldNullEnum;
    }

    public boolean isSyncTaskEnable() {
        return syncTaskEnable;
    }

    public long getSyncTaskTimeout() {
        return syncTaskTimeout;
    }

    public boolean getSyncTaskDeleteKey() {
        return syncTaskDeleteKey;
    }

    @Bean
    public JWT jwt() {
        return new JWT(key);
    }
}
