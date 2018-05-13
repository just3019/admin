package org.demon.util;

/**
 * Description:
 * Date: 2016/11/1 14:06
 *
 * @author Sean.xie
 */
public class PatternConstants {
    public static final String PHONE = "^((13[0-9])|(14[5,7])|(15[^4&&\\d])|(17[{0-9}])|(18[0-9]))\\d{8}$";
    public static final String SIMPLE_PHONE = "^[1][0-9]{10}$";
    public static final String EMAIL = "^\\w+([-_.]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,6})+$";
}
