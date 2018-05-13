package org.demon.util;


/**
 * Description:
 * Date: 2016/11/12 11:34
 *
 * @author Sean.xie
 */
public class NumberUtil {
    /**
     * All possible chars for representing a number as a String
     */
    private final static char[] DIGITS = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z',
    };
    /**
     * DIGITS 对应的值
     */
    private final static byte[] VALUES;

    private final static int MIN_RADIX = 2;
    private final static int MAX_RADIX = 62;

    static {
        // 初始化VALUES
        VALUES = new byte[128];
        for (int i = 0; i < 128; i++) {
            VALUES[i] = 0;
        }
        int value = 0;
        for (int i = 0; i < DIGITS.length; i++) {
            VALUES[DIGITS[i]] = (byte) (i);
        }
    }

    /**
     * 检查id 有效性
     */
    public static boolean isValidId(Long id) {
        return (id != null && id > 0);
    }

    /**
     * 检查id 有效性
     */
    public static boolean isInValidId(Long id) {
        return (id == null || id <= 0);
    }

    /**
     * 检查id 有效性
     */
    public static boolean isValidId(Integer id) {
        return (id != null && id > 0);
    }

    /**
     * 检查id 有效性
     */
    public static boolean isInValidId(Integer id) {
        return (id == null || id <= 0);
    }

    /**
     * 是否为正数
     *
     * @param number 数字
     */
    public static boolean isPositive(Integer number) {
        return (number != null && number > 0);
    }

    /**
     * 是否为负数
     *
     * @param number 数字
     */
    public static boolean isNegative(Integer number) {
        return (number != null && number < 0);
    }

    /**
     * 是否为正数
     *
     * @param number 数字
     */
    public static boolean isPositive(Long number) {
        return (number != null && number > 0);
    }

    /**
     * 是否为负数
     *
     * @param number 数字
     */
    public static boolean isNegative(Long number) {
        return (number != null && number < 0);
    }

    /**
     * 10以内随机数
     */
    public static int getRandomInTen() {
        return (int) (Math.random() * 10);
    }

    /**
     * 指定值以内随机数
     */
    public static int getRandomInValue(int value) {
        return (int) (Math.random() * value);
    }

    /**
     * 十进制字符串转化Long
     */
    public static Long getLong(String value) {
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 转换Long
     */
    public static long getLong(Long value, long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 对象转Long
     */
    public static Long getLongFromObj(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        return getLong(obj.toString());
    }

    /**
     * 十进制字符串转化long
     */
    public static long getLong(String value, long defaultValue) {
        Double doubleValue = getDouble(value);
        long result = defaultValue;
        if (doubleValue != null) {
            result = doubleValue.longValue();
        }
        return result;
    }

    /**
     * 十进制字符串转换int
     */
    public static int getInteger(String value, int defaultValue) {
        Double doubleValue = getDouble(value);
        int result = defaultValue;
        if (doubleValue != null) {
            result = doubleValue.intValue();
        }
        return result;
    }

    /**
     * 对象转 Integer
     */
    public static Integer getIntegerFromObj(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return getInteger(obj.toString());
    }

    /**
     * 十进制字符串转换 Integer
     */
    public static Integer getInteger(String value) {
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 转换 Double
     */
    public static Double getDouble(String value) {
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        try {
            return Double.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 转换为62进制
     */
    public static String to62RadixString(long i) {
        return toRadixString(i, 62);
    }

    /**
     * 进制转换
     */
    public static String toRadixString(long i, int radix) {
        if (radix < MIN_RADIX || radix > MAX_RADIX)
            radix = 10;
        if (radix == 10)
            return Long.toString(i);
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);

        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = DIGITS[(int) (-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = DIGITS[(int) (-i)];

        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, (65 - charPos));
    }

    /**
     * 字符串转换为long, 若超出范围则失败
     */
    public static long radixValue2Long(String value, int radix) {
        return parseLong(value, radix);
    }

    /**
     * 字符串转换为int ,安全算法,如果超int 范围则精度丢失
     */
    public static int radixValue2Int(String value, int radix) {
        return (int) radixValue2Long(value, radix);
    }

    /**
     * 字符串转换为int ,安全算法,如果超int 范围则失败
     */
    public static int radixValue2SafeInt(String value, int radix) {
        long result = radixValue2Long(value, radix);
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw forInputString(value);
        }
        return (int) result;
    }

    /**
     * 非十进制转为十进制
     *
     * @param value 非十进制字符串
     * @param radix 原始串进制数
     */
    public static long parseLong(String value, int radix)
            throws NumberFormatException {
        if (value == null) {
            throw new NumberFormatException("null");
        }

        if (radix < MIN_RADIX) {
            throw new NumberFormatException("radix " + radix + " less than MIN_RADIX");
        }
        if (radix > MAX_RADIX) {
            throw new NumberFormatException("radix " + radix + " greater than MAX_RADIX");
        }


        long result = 0;
        boolean negative = false;
        int i = 0, len = value.length();
        long limit = -Long.MAX_VALUE;
        long multmin;
        int digit;

        if (len > 0) {
            char firstChar = value.charAt(0);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
                } else if (firstChar != '+')
                    throw forInputString(value);

                if (len == 1) // Cannot have lone "+" or "-"
                    throw forInputString(value);
                i++;
            }
            multmin = limit / radix;

            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                if (radix > Character.MAX_RADIX) {
                    digit = VALUES[value.charAt(i++)];
                } else {
                    digit = Character.digit(value.charAt(i++), radix);
                }
                if (digit < 0) {
                    throw forInputString(value);
                }
                if (result < multmin) {
                    throw forInputString(value);
                }
                result *= radix;
                if (result < limit + digit) {
                    throw forInputString(value);
                }
                result -= digit;
            }
        } else {
            throw forInputString(value);
        }
        return negative ? result : -result;
    }

    /**
     * 数字转换异常
     */
    private static NumberFormatException forInputString(String s) {
        return new NumberFormatException("For input string: \"" + s + "\"");
    }

    /**
     * 将任意进制转换成十进制数
     */
    private static long parseLarger36(String value, int radix) {
        int len = value.length();
        long result = 0;
        for (int i = 0; i < len; i++) {
            result += VALUES[value.charAt(i)] * ((long) Math.pow(radix, len - 1 - i));
        }
        return result;
    }


    /**
     * 四位数字
     */
    public static String random() {
        String str = "";
        str += (int) (Math.random() * 9 + 1);
        for (int i = 0; i < 3; i++) {
            str += (int) (Math.random() * 10);
        }
        return str;
    }

    /**
     * 是否纯数字
     */
    public static boolean checkNum(String value) {
        return !StringUtil.isEmpty(value) && value.matches("\\d+");
    }

    /**
     * 比较 Integer
     */
    public static boolean equals(Integer v1, Integer v2) {
        return !(v1 == null || v2 == null) && v1.equals(v2);
    }

    /**
     * 比较 Long
     */
    public static boolean equals(Long v1, Long v2) {
        return !(v1 == null || v2 == null) && v1.equals(v2);
    }

    /**
     * 比较 Long
     */
    public static boolean equals(Long v1, Integer v2) {
        return !(v1 == null || v2 == null) && v1.equals(v2.longValue());
    }

    /**
     * 差值
     */
    public static Long delta(Long v1, Long v2) {
        if (v1 == null || v2 == null) {
            return null;
        }
        return v1 - v2;
    }

    /**
     * 差值
     */
    public static Integer delta(Integer v1, Integer v2) {
        if (v1 == null || v2 == null) {
            return null;
        }
        return v1 - v2;
    }

    /**
     * 差值
     */
    public static Long delta(Long v1, Integer v2) {
        if (v1 == null || v2 == null) {
            return null;
        }
        return v1 - v2;
    }
}
