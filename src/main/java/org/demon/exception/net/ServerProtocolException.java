package org.demon.exception.net;

/**
 * Created by Sean.xie on 2016/1/28.
 */
public class ServerProtocolException extends BaseNetException {

    public ServerProtocolException() {
        super();
    }

    public ServerProtocolException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServerProtocolException(String detailMessage) {
        super(detailMessage);
    }

    public ServerProtocolException(Throwable throwable) {
        super(throwable);
    }
}
