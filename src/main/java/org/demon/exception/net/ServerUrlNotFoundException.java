package org.demon.exception.net;

/**
 * Created by Sean.xie on 2016/1/28.
 */
public class ServerUrlNotFoundException extends BaseNetException {

    public ServerUrlNotFoundException() {
        super();
    }

    public ServerUrlNotFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServerUrlNotFoundException(String detailMessage) {
        super(detailMessage);
    }

    public ServerUrlNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
