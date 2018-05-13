package org.demon.util;

/**
 * Description:
 * Created by Sean.xie on 2017/3/8.
 */
public final class ThreadUtil {
    private static Logger logger = Logger.newInstance(ThreadUtil.class);

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            logger.printStackTrace(e);
        }
    }

}
