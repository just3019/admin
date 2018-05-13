package org.demon.init;


import org.demon.Constants;
import org.demon.config.BuildConfig;
import org.demon.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;

/**
 * Description: 不支持Feign 的启动配置
 * Created by Sean.xie on 2017/2/6.
 */
@Configuration
@ImportResource(locations = {"classpath:springContext.xml"})
@ServletComponentScan({"org.demon.filter","org.demon.listener", "org.demon.controller"})
public class ApplicationEnvConfig {
    // 是否是调试模式
    public static boolean DEBUG = true;
    public static String JWT_KEY = null;
    private final Logger logger = Logger.newInstance(ApplicationEnvConfig.class);
    @Autowired
    private BuildConfig buildConfig;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        Constants.IS_DEBUG = buildConfig.isDebug();
        DEBUG = buildConfig.isDebug();
        JWT_KEY = buildConfig.getJWTKey();
        if (DEBUG) {
            logger.info("BuildConfig enable_debug:{}", DEBUG);
        }
    }

}
