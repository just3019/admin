package org.demon.exception.net;

public class ParameterErrorException extends RuntimeException {

    public ParameterErrorException() {
        super();
    }

    public ParameterErrorException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ParameterErrorException(String detailMessage) {
        super(detailMessage);
    }

    public ParameterErrorException(Throwable throwable) {
        super(throwable);
    }

}
