package org.demon.util;


import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;


/** */
public final class Logger {
    private final org.slf4j.Logger logger;

    private Logger(Class clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public static Logger newInstance(Class clazz) {
        return new Logger(clazz);
    }

    private static String getDebugInfo() {
        StackTraceElement[] lvStacks = Thread.currentThread().getStackTrace();
        int layout = 4;//第4层代表,调用Logger.[info,debug,warn,error] 的方法, 如果不明白为什么,debug一下看栈信息就知道原因了
        return "Class Name：[ " + lvStacks[layout].getClassName() + " ],Function Name：[ " + lvStacks[layout].getMethodName()
                + " ],Line：[ " + lvStacks[layout].getLineNumber() + " ]\n";
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     *
     * @param tr An exception to log
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return " UnknownHostException ";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public void debug(String message, Object... params) {
        logger.debug(message, params);
    }

    public void info(String message, Object... params) {
        logger.info(message, params);
    }

    public void warn(String message, Object... params) {
        logger.warn(getMessage(message), params);
    }

    public void warn(String message, Throwable t) {
        logger.warn(message, t);
    }

    public void error(String message, Object... params) {
        logger.error(getMessage(message), params);
    }

    private String getMessage(String message) {
        return getDebugInfo() + message;
    }

    public void printStackTrace(Throwable e) {
        if (true) {
            e.printStackTrace();
        }
        logger.warn("Logger", e);
    }

    public void printDebugStackTrace(Throwable e) {
        if (true) {
            e.printStackTrace();
            logger.info("Logger", e);
        }
    }
}
