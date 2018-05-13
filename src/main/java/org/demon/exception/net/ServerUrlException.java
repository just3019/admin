package org.demon.exception.net;

/**
 * Created by Sean.xie on 2016/1/28.
 */
public class ServerUrlException extends BaseNetException {

    public ServerUrlException() {
        super();
    }

    public ServerUrlException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServerUrlException(String detailMessage) {
        super(detailMessage);
    }

    public ServerUrlException(Throwable throwable) {
        super(throwable);
    }
}
