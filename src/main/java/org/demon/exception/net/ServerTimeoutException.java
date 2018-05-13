package org.demon.exception.net;

/**
 * Created by Sean.xie on 2016/1/28.
 */
public class ServerTimeoutException extends BaseNetException {

    public ServerTimeoutException() {
        super();
    }

    public ServerTimeoutException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServerTimeoutException(String detailMessage) {
        super(detailMessage);
    }

    public ServerTimeoutException(Throwable throwable) {
        super(throwable);
    }
}
