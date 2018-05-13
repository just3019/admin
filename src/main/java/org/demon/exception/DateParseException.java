package org.demon.exception;

/**
 * Description:
 * Date: 2016/10/13 19:57
 *
 * @author Sean.xie
 */
public class DateParseException extends BaseException {
    public DateParseException() {
    }

    public DateParseException(String message) {
        super(message);
    }

    public DateParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateParseException(Throwable cause) {
        super(cause);
    }

    public DateParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
