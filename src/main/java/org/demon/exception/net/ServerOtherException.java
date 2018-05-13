package org.demon.exception.net;

/**
 * Created by Sean.xie on 2016/1/28.
 */
public class ServerOtherException extends BaseNetException {

    public ServerOtherException() {
        super();
    }

    public ServerOtherException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServerOtherException(String detailMessage) {
        super(detailMessage);
    }

    public ServerOtherException(Throwable throwable) {
        super(throwable);
    }
}
