package org.demon.exception;

import org.demon.bean.BaseResult;

/**
 * Description: 不打印栈信息
 * Created by Sean.xie on 2017/10/26.
 */
public class NoStackTraceCodeException extends CodeException {
    public NoStackTraceCodeException(ExceptionInfo code, String... msgs) {
        super(code, msgs);
    }

    public NoStackTraceCodeException(BaseResult result) {
        super(result);
    }

    protected boolean fillStackTrace() {
        return false;
    }
}
