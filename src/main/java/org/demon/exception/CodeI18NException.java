package org.demon.exception;

import org.demon.bean.BaseResult;
import org.demon.util.I18nUtil;
import org.demon.util.StringUtil;

import java.util.Arrays;
import java.util.Locale;

/**
 * Description: 没有栈信息的Exception
 * Date: 2016/10/9 19:18
 *
 * @author Sean.xie
 */
public class CodeI18NException extends CodeException {

    /**
     * 设置 错误码和错误信息
     * msg 只能使用 error_base_strings.properties 的key
     * {@link org.demon.util.I18nUtil#getLocalResource(Locale, String, String, Object...)}
     *
     * @param code 错误错误信息
     */
    public CodeI18NException(ExceptionInfo code, String... msgs) {
        super(code, msgs);
    }

    public CodeI18NException(BaseResult result) {
        super(result);
    }

    /**
     * 业务文案, 放在values/biz_string.properties
     *
     */
    public static String getString(String key, Object... args) {
        return I18nUtil.getLocalResource(Locale.getDefault(), "values/biz_strings", key, args);
    }

    @Override
    protected String msg(String... msgs) {
        if (msgs == null) {
            return "";
        }
        if (msgs.length == 1) {
            return StringUtil.formatContent(getString(msgs[0]));
        } else {
            Object[] args = Arrays.copyOfRange(msgs, 1, msgs.length);
            return StringUtil.formatContent(getString(msgs[0], args));
        }
    }
}
