package org.demon.exception.net;

/**
 * Created by Sean.xie on 2016/1/28.
 */
public class ServerIOException extends BaseNetException {

    public ServerIOException() {
        super();
    }

    public ServerIOException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServerIOException(String detailMessage) {
        super(detailMessage);
    }

    public ServerIOException(Throwable throwable) {
        super(throwable);
    }
}
