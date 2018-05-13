package org.demon.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Description: Spring Context
 * Date: 2016/10/21 18:59
 *
 * @author Sean.xie
 */
@Component
public class SpringAPPContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringAPPContext.applicationContext = applicationContext;
    }

    /**
     * 获取Bean 实例
     *
     * @param name 实例类名
     */
    public static <T> T getBean(String name) {
        try {
            if (applicationContext == null) {

            }
            return (T) applicationContext.getBean(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Bean 实例
     *
     * @param clazz 实例类型
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            return (T) applicationContext.getBean(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取环境变量
     *
     * @param key Key
     */
    public static String getEnvironmentValue(String key) {
        try {
            return applicationContext.getEnvironment().getProperty(key);
        } catch (Exception e) {
            // nothing to do
        }
        return null;
    }
}