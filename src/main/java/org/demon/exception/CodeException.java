package org.demon.exception;


import org.demon.bean.BaseResult;
import org.demon.enums.EnumValue;
import org.demon.util.StringUtil;

import java.io.PrintStream;
import java.util.Locale;

/**
 * Description: 没有栈信息的Exception
 * Date: 2016/10/9 19:18
 *
 * @author Sean.xie
 */
public class CodeException extends BaseRuntimeException {

    /**
     * 错误信息
     */
    protected final ExceptionInfo code;

    /**
     * 错误数据
     */
    protected Object data;

    /**
     * 设置 错误码和错误信息
     * msg 只能使用 error_base_strings.properties 的key
     * {@link org.demon.util.I18nUtil#getLocalResource(Locale, String, String, Object...)}
     *
     * @param code 错误错误信息
     */
    public CodeException(ExceptionInfo code, String... msgs) {
        // TODO code can not null
        String msg = StringUtil.isEmpty(msgs) ? code.getMsg() : msg(msgs);
        this.code = new CodeExceptionUtil.CodeStatus(code.getCode(), msg);
    }

    public CodeException(BaseResult result) {
        this.code = new CodeExceptionUtil.CodeStatus(result.getState(), result.getErrormsg());
    }

    public String getMsg() {
        return code.getMsg();
    }

    /**
     * 用于子类扩展
     *
     * @param msgs 消息内容
     * @return 提示文案
     */
    protected String msg(String... msgs) {
        StringBuffer sb = new StringBuffer();
        for (String m : msgs) {
            sb.append(m).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 设置级别
     */
    public CodeException setLevel(Level level) {
        code.setLevel(level);
        return this;
    }

    public ExceptionInfo getCode() {
        return code;
    }

    /**
     * 获取 Data
     */
    @Deprecated
    public Object getData() {
        return data;
    }

    /**
     * 设置 Data
     */
    @Deprecated
    public CodeException setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String getMessage() {
        return getMsg();
    }

    /**
     * 异常信息
     */
    private String getExceptionInfo() {
        if (code == null && data == null) {
            return "";
        }
        StringBuilder msg = new StringBuilder();
        if (code != null) {
            msg.append("Exception - code : ").append(code.getCode())
                    .append("\n msg : ").append(code.getMsg());
        }
        if (data != null) {
            msg.append("\n data : ").append(data);
        }
        return msg.toString();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        if (fillStackTrace()) {
            super.fillInStackTrace();
        }
        return this;
    }

    protected boolean fillStackTrace() {
        return true;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        if (fillStackTrace()) {
            return super.getStackTrace();
        }
        StackTraceElement element = new StackTraceElement(getExceptionInfo(), "", null, -1);
        return new StackTraceElement[]{element};
    }

    @Override
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     *
     * @param s {@code PrintStream} to use for output
     */
    @Override
    public void printStackTrace(PrintStream s) {
        if (true) {
            super.printStackTrace(s);
        }
    }

    public enum Level implements EnumValue<Integer> {
        NONE(null), ERROR(0), WARNING(1), INFO(2);

        private Integer value;

        Level(Integer value) {
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 异常信息错误码和文案
     */
    public interface ExceptionInfo {

        public int getCode();

        public String getMsg();

        public default Integer getLevel() {
            return Level.NONE.getValue();
        }

        public default ExceptionInfo setLevel(CodeException.Level level) {
            return this;
        }
    }
}
