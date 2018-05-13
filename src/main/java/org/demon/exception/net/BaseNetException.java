package org.demon.exception.net;

import org.demon.exception.BaseException;

/**
 * Created by doris on 2016/1/31.
 */
public class BaseNetException extends BaseException {

    public BaseNetException() {
        super();
    }

    public BaseNetException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public BaseNetException(String detailMessage) {
        super(detailMessage);
    }

    public BaseNetException(Throwable throwable) {
        super(throwable);
    }

}
