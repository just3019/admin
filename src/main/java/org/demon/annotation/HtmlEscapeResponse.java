package org.demon.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: Web 返回数据格式
 * Date: 2016/10/11 17:48
 *
 * @author Sean.xie
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface HtmlEscapeResponse {

    /**
     * 返回安全的html {@link org.demon.util.StringUtil#safeHtml(String)}
     *
     * @return
     */
    boolean safeHtml() default false;
}
