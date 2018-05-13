package org.demon.exception;

/**
 * Description:
 * Date: 2016/10/11 14:21
 *
 * @author Sean.xie
 */
public class BaseRuntimeException extends RuntimeException {
    public BaseRuntimeException() {
    }

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

    public BaseRuntimeException(String message, Throwable cause, boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
