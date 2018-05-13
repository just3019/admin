package org.demon.exception.net;

/**
 * Created by Sean.xie on 2016/1/28.
 */
public class ParseBeanException extends BaseNetException {

    public ParseBeanException() {
        super();
    }

    public ParseBeanException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ParseBeanException(String detailMessage) {
        super(detailMessage);
    }

    public ParseBeanException(Throwable throwable) {
        super(throwable);
    }
}
