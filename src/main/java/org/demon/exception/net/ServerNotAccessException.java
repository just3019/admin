package org.demon.exception.net;

/**
 * Created by Sean.xie on 2016/1/28.
 */
public class ServerNotAccessException extends BaseNetException {

    public ServerNotAccessException() {
        super();
    }

    public ServerNotAccessException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServerNotAccessException(String detailMessage) {
        super(detailMessage);
    }

    public ServerNotAccessException(Throwable throwable) {
        super(throwable);
    }
}
