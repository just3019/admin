package org.demon.exception.net;

/**
 * Created by Sean.xie on 2016/1/28.
 */
public class ServerDataException extends BaseNetException {

    public ServerDataException() {
        super();
    }

    public ServerDataException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServerDataException(String detailMessage) {
        super(detailMessage);
    }

    public ServerDataException(Throwable throwable) {
        super(throwable);
    }
}
