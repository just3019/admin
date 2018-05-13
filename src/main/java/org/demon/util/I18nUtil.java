package org.demon.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Description:
 * Date: 2016/10/10 10:46
 *
 * @author Sean.xie
 */
public class I18nUtil {

    /**
     * 业务文案, 放在values/biz_string.properties
     *
     * @param key
     * @param args
     * @return
     */
    public static String getString(String key, Object... args) {
        return I18nUtil.getLocalResource(Locale.getDefault(), "values/biz_strings", key, args);
    }

    /**
     * 错误文案, 放在values/error_strings.properties
     *
     * @param key
     * @param args
     * @return
     */
    public static String getErrorMsg(String key, Object... args) {
        return I18nUtil.getLocalResource(Locale.getDefault(), "values/error_strings", key, args);
    }

    /**
     * 获取本地资源
     *
     * @param locale   local
     * @param baseName 资源文件名
     * @param key      key
     * @param args     参数
     * @return
     */
    public static String getLocalResource(Locale locale, String baseName, String key, Object... args) {
        // 获得资源文件
        ResourceBundle rb = ResourceBundle.getBundle(baseName, locale);

        // 获得相应的key值
        String message = rb.getString(key);
        if (args != null && args.length > 0) {
            return MessageFormat.format(message, args);
        } else {
            return message;
        }
    }

    /**
     * 获取jar包外的配置文件
     *
     * @param propertyPath
     * @return
     */
    public static ResourceBundle getResourceBundle(String propertyPath) {
        ResourceBundle bundle = null;
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(propertyPath));
            bundle = new PropertyResourceBundle(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return bundle;
    }

}
