package org.demon.util;


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"JavaDoc", "unchecked", "WeakerAccess", "unused"})
public class StringUtil {
    private static Logger logger = Logger.newInstance(StringUtil.class);

    /**
     * 把控字符转换成空串
     *
     * @param str
     * @return
     */
    public static String transNull(String str) {
        if (str != null) {
            return str;
        } else {
            return "";
        }
    }

    /**
     * 把字符串根据分隔符生成数组。
     *
     * @param line  字符串
     * @param delim 分隔符
     * @return String[] 字符串数组
     */
    public static String[] split(String line, String delim) {
        if (line == null) {
            return new String[0];
        }
        List list = new ArrayList();
        StringTokenizer t = new StringTokenizer(line, delim);

        while (t.hasMoreTokens()) {
            list.add(t.nextToken());
        }

        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * 解决中文的乱码问题
     *
     * @param chi 为输入的要汉化的字符串
     * @return String 经过转换后的字符串
     */
    public static String transChiTo8859(String chi) {

        if (StringUtil.isEmpty(chi))
            return "";

        String result = null;
        byte temp[];
        try {
            temp = chi.getBytes("GBK");
            result = new String(temp, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.printStackTrace(e);
        }

        return result;

    }

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * map 是否为空
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    /**
     * 集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static boolean equalsIgnoreCase(String v1, String v2) {
        if (v1 == null || v2 == null) {
            return false;
        }
        return v1.equalsIgnoreCase(v2);
    }

    /**
     * 数组是否为空
     *
     * @param array
     * @return
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isTrue(Boolean value) {
        return value != null && value;
    }

    public static boolean isTrue(String value) {
        return isTrue(Boolean.valueOf(value));
    }

    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     * <p>
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     * <p>
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     * not empty and not null and not whitespace
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }


    /**
     * 判断是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        if (obj instanceof String) {
            return isEmpty((String) obj);
        }
        return obj == null;
    }

    /**
     * 检查是否为空
     *
     * @param obj
     */
    public static void checkNull(Object obj) {
        if (isNull(obj)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 格式化文本
     * example:
     * formatContent("我是{0}","Sean")
     * result:  我是Sean
     *
     * @param content content
     * @param args    参数
     * @return
     */
    public static String formatContent(String content, Object... args) {
        if (args != null && args.length > 0) {
            return MessageFormat.format(content, args);
        } else {
            return content;
        }
    }

    /**
     * 金额
     *
     * @param number_i
     * @return
     */
    public static String getChnmoney(double number_i) {
        String strNum = String.valueOf(number_i);
        int n, m, k, i, j, q, p, r, s = 0;
        int length, subLength, pstn;
        String change, output, subInput;
        output = "";
        if (strNum.equals(""))
            return null;
        else {
            length = strNum.length();
            pstn = strNum.indexOf('.'); // 小数点的位置

            if (pstn == -1) {
                subLength = length;// 获得小数点前的数字
                subInput = strNum;
            } else {
                subLength = pstn;
                subInput = strNum.substring(0, subLength);
            }

            char[] array = new char[4];
            char[] array2 = {'仟', '佰', '拾'};
            char[] array3 = {'亿', '万', '元', '角', '分'};

            n = subLength / 4;// 以千为单位
            m = subLength % 4;

            if (m != 0) {
                for (i = 0; i < (4 - m); i++) {
                    subInput = '0' + subInput;// 补充首位的零以便处理
                }
                n = n + 1;
            }
            k = n;

            for (i = 0; i < n; i++) {
                p = 0;
                change = subInput.substring(4 * i, 4 * (i + 1));
                array = change.toCharArray();// 转换成数组处理

                for (j = 0; j < 4; j++) {
                    output += formatChineseNumber(array[j]);// 转换成中文
                    if (j < 3) {
                        output += array2[j];// 补进单位，当为零是不补（千百十）
                    }
                    p++;
                }

                if (p != 0)
                    output += array3[3 - k];// 补进进制（亿万元分角）
                // 把多余的零去掉

                String[] str = {"零仟", "零佰", "零拾"};
                for (s = 0; s < 3; s++) {
                    while (true) {
                        q = output.indexOf(str[s]);
                        if (q != -1)
                            output = output.substring(0, q) + "零"
                                    + output.substring(q + str[s].length());
                        else
                            break;
                    }
                }
                while (true) {
                    q = output.indexOf("零零");
                    if (q != -1)
                        output = output.substring(0, q) + "零"
                                + output.substring(q + 2);
                    else
                        break;
                }
                String[] str1 = {"零亿", "零万", "零元"};
                for (s = 0; s < 3; s++) {
                    while (true) {
                        q = output.indexOf(str1[s]);
                        if (q != -1)
                            output = output.substring(0, q)
                                    + output.substring(q + 1);
                        else
                            break;
                    }
                }
                k--;
            }

            if (pstn != -1)// 小数部分处理
            {
                for (i = 1; i < length - pstn; i++) {
                    if (strNum.charAt(pstn + i) != '0') {
                        output += formatChineseNumber(strNum.charAt(pstn + i));
                        output += array3[2 + i];
                    } else if (i < 2)
                        output += "零";
                    else
                        output += "";
                }
            }
            if (output.substring(0, 1).equals("零"))
                output = output.substring(1);
            if (output.substring(output.length() - 1, output.length()).equals("零"))
                output = output.substring(0, output.length() - 1);
            return (output += "整");
        }
    }

    /**
     * 汉子大写数字
     *
     * @param x
     * @return
     */
    public static String formatChineseNumber(char x) {
        String a = "";
        switch (x) {
            case '0':
                a = "零";
                break;
            case '1':
                a = "壹";
                break;
            case '2':
                a = "贰";
                break;
            case '3':
                a = "叁";
                break;
            case '4':
                a = "肆";
                break;
            case '5':
                a = "伍";
                break;
            case '6':
                a = "陆";
                break;
            case '7':
                a = "柒";
                break;
            case '8':
                a = "捌";
                break;
            case '9':
                a = "玖";
                break;
        }
        return a;
    }

    // GENERAL_PUNCTUATION 判断中文的“号

    // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号

    // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;

    }

    /**
     * 手机号简单校验
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        if (isEmpty(mobile)) {
            return false;
        }
        // 国际号码客户端传递过来格式：区号 + “-” + 号码
        if (!"".equals(mobile) && mobile.contains("-")) {
            return true;
        }
        Pattern p = Pattern.compile(PatternConstants.SIMPLE_PHONE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 手机号验证,严格
     *
     * @param mobile
     * @return
     */
    public static boolean isRealMobile(String mobile) {
        return !isEmpty(mobile) && mobile.matches(PatternConstants.PHONE);
    }

    /**
     * 检测邮箱格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        boolean tag;
        final Pattern pattern = Pattern.compile(PatternConstants.EMAIL);
        final Matcher mat = pattern.matcher(email);
        try {
            tag = mat.matches();
        } catch (Exception exception) {
            tag = false;
        }

        return tag;
    }

    /**
     * 随机生成字母串
     *
     * @param len
     * @return
     */
    public static String getRandomLetter(int len) {
        String str = "";
        for (int i = 0; i < len; i++) {// 你想生成几个字符的，就把len改成几．
            str = str + NumberUtil.to62RadixString((int) (Math.random() * 63));
        }
        return str;
    }

    /**
     * 随机生成小写字母串
     *
     * @param len
     * @return
     */
    public static String getRandomLetterDownCase(int len) {
        String str = "";
        for (int i = 0; i < len; i++) {// 你想生成几个字符的，就把len改成几．
            str = str + (char) (Math.random() * 26 + 'a');
        }
        return str;
    }

    /**
     * 随机生成大写字母串
     *
     * @param len
     * @return
     */
    public static String getRandomLetterUpCase(int len) {
        String str = "";
        for (int i = 0; i < len; i++) {// 你想生成几个字符的，就把len改成几．
            str = str + (char) (Math.random() * 26 + 'A');
        }
        return str;
    }

    /**
     * 删除html中引起安全问题的标签
     *
     * @param html
     * @return
     */
    public static String safeHtml(String html) {
        String result = html;
        List<String> unSafeLables = new ArrayList<>();
        unSafeLables.add("<iframe[^>]*?>.*?</iframe>");
        unSafeLables.add("<frame[^>]*?>.*?</frame>");
        unSafeLables.add("<script[^>]*?>.*?</script>");
        unSafeLables.add("<title[^>]*?>.*?</title>");
        unSafeLables.add("<meta[^>]*?>");
        unSafeLables.add("<noscript[^>]*?>");
        unSafeLables.add("href=\"javascript:.*?\"");
//        unSafeLables.add("<head[^>]*?>.*?</head>");
//        unSafeLables.add("<link[^>]*?>");
        for (String lable : unSafeLables) {
            result = result.replaceAll(lable, "");
        }
        return result;
    }


    /**
     * <p>
     * Compares two CharSequences, returning <code>true</code> if they represent equal sequences of characters.
     * </p>
     * <p>
     * <p>
     * <code>null</code>s are handled without exceptions. Two <code>null</code> references are considered to be equal.
     * The comparison is case sensitive.
     * </p>
     * <p>
     * <pre>
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     * </pre>
     * <p>
     * <p>
     * Copied from Apache Commons Lang r1583482 on April 10, 2014 (day of 3.3.2 release).
     * </p>
     *
     * @param cs1 the first CharSequence, may be <code>null</code>
     * @param cs2 the second CharSequence, may be <code>null</code>
     * @return <code>true</code> if the CharSequences are equal (case-sensitive), or both <code>null</code>
     * @see Object#equals(Object)
     * @since 1.10
     */
    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            return cs1.equals(cs2);
        }
        return CharSequenceUtils.regionMatches(cs1, false, 0, cs2, 0, Math.max(cs1.length(), cs2.length()));
    }

    /**
     * Calls {@link String#getBytes(Charset)}
     *
     * @param string  The string to encode (if null, return null).
     * @param charset The {@link Charset} to encode the <code>String</code>
     * @return the encoded bytes
     */
    private static byte[] getBytes(final String string, final Charset charset) {
        if (string == null) {
            return null;
        }
        return string.getBytes(charset);
    }

    /**
     * Encodes the given string into a sequence of bytes using the ISO-8859-1 charset, storing the result into a new
     * byte array.
     *
     * @param string the String to encode, may be <code>null</code>
     * @return encoded bytes, or <code>null</code> if the input string was <code>null</code>
     * @throws NullPointerException Thrown if {@link StandardCharsets#ISO_8859_1} is not initialized, which should
     * never happen since it is
     *                              required by the Java platform specification.
     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @see #getBytesUnchecked(String, String)
     * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
     */
    public static byte[] getBytesIso8859_1(final String string) {
        return getBytes(string, StandardCharsets.ISO_8859_1);
    }


    /**
     * Encodes the given string into a sequence of bytes using the named charset, storing the result into a new byte
     * array.
     * <p>
     * This method catches {@link UnsupportedEncodingException} and rethrows it as {@link IllegalStateException}, which
     * should never happen for a required charset name. Use this method when the encoding is required to be in the JRE.
     * </p>
     *
     * @param string      the String to encode, may be <code>null</code>
     * @param charsetName The name of a required {@link Charset}
     * @return encoded bytes, or <code>null</code> if the input string was <code>null</code>
     * @throws IllegalStateException Thrown when a {@link UnsupportedEncodingException} is caught, which should never
     * happen for a
     *                               required charset name.
     * @see String#getBytes(String)
     */
    public static byte[] getBytesUnchecked(final String string, final String charsetName) {
        if (string == null) {
            return null;
        }
        try {
            return string.getBytes(charsetName);
        } catch (final UnsupportedEncodingException e) {
            throw StringUtil.newIllegalStateException(charsetName, e);
        }
    }

    /**
     * Encodes the given string into a sequence of bytes using the US-ASCII charset, storing the result into a new byte
     * array.
     *
     * @param string the String to encode, may be <code>null</code>
     * @return encoded bytes, or <code>null</code> if the input string was <code>null</code>
     * @throws NullPointerException Thrown if {@link StandardCharsets#US_ASCII} is not initialized, which should
     * never happen since it is
     *                              required by the Java platform specification.
     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @see #getBytesUnchecked(String, String)
     * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
     */
    public static byte[] getBytesUsAscii(final String string) {
        return getBytes(string, StandardCharsets.US_ASCII);
    }

    /**
     * Encodes the given string into a sequence of bytes using the UTF-16 charset, storing the result into a new byte
     * array.
     *
     * @param string the String to encode, may be <code>null</code>
     * @return encoded bytes, or <code>null</code> if the input string was <code>null</code>
     * @throws NullPointerException Thrown if {@link StandardCharsets#UTF_16} is not initialized, which should never
     * happen since it is
     *                              required by the Java platform specification.
     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @see #getBytesUnchecked(String, String)
     * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
     */
    public static byte[] getBytesUtf16(final String string) {
        return getBytes(string, StandardCharsets.UTF_16);
    }

    /**
     * Encodes the given string into a sequence of bytes using the UTF-16BE charset, storing the result into a new byte
     * array.
     *
     * @param string the String to encode, may be <code>null</code>
     * @return encoded bytes, or <code>null</code> if the input string was <code>null</code>
     * @throws NullPointerException Thrown if {@link StandardCharsets#UTF_16BE} is not initialized, which should
     * never happen since it is
     *                              required by the Java platform specification.
     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @see #getBytesUnchecked(String, String)
     * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
     */
    public static byte[] getBytesUtf16Be(final String string) {
        return getBytes(string, StandardCharsets.UTF_16BE);
    }

    /**
     * Encodes the given string into a sequence of bytes using the UTF-16LE charset, storing the result into a new byte
     * array.
     *
     * @param string the String to encode, may be <code>null</code>
     * @return encoded bytes, or <code>null</code> if the input string was <code>null</code>
     * @throws NullPointerException Thrown if {@link StandardCharsets#UTF_16LE} is not initialized, which should
     * never happen since it is
     *                              required by the Java platform specification.
     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @see #getBytesUnchecked(String, String)
     * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
     */
    public static byte[] getBytesUtf16Le(final String string) {
        return getBytes(string, StandardCharsets.UTF_16LE);
    }

    /**
     * Encodes the given string into a sequence of bytes using the UTF-8 charset, storing the result into a new byte
     * array.
     *
     * @param string the String to encode, may be <code>null</code>
     * @return encoded bytes, or <code>null</code> if the input string was <code>null</code>
     * @throws NullPointerException Thrown if {@link StandardCharsets#UTF_8} is not initialized, which should never
     * happen since it is
     *                              required by the Java platform specification.
     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @see #getBytesUnchecked(String, String)
     * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
     */
    public static byte[] getBytesUtf8(final String string) {
        return getBytes(string, StandardCharsets.UTF_8);
    }

    private static IllegalStateException newIllegalStateException(final String charsetName,
                                                                  final UnsupportedEncodingException e) {
        return new IllegalStateException(charsetName + ": " + e);
    }

    /**
     * Constructs a new <code>String</code> by decoding the specified array of bytes using the given charset.
     *
     * @param bytes   The bytes to be decoded into characters
     * @param charset The {@link Charset} to encode the <code>String</code>
     * @return A new <code>String</code> decoded from the specified array of bytes using the given charset,
     * or <code>null</code> if the input byte array was <code>null</code>.
     * @throws NullPointerException Thrown if {@link StandardCharsets#UTF_8} is not initialized, which should never
     * happen since it is
     *                              required by the Java platform specification.
     */
    private static String newString(final byte[] bytes, final Charset charset) {
        return bytes == null ? null : new String(bytes, charset);
    }

    /**
     * Constructs a new <code>String</code> by decoding the specified array of bytes using the given charset.
     * <p>
     * This method catches {@link UnsupportedEncodingException} and re-throws it as {@link IllegalStateException}, which
     * should never happen for a required charset name. Use this method when the encoding is required to be in the JRE.
     * </p>
     *
     * @param bytes       The bytes to be decoded into characters, may be <code>null</code>
     * @param charsetName The name of a required {@link Charset}
     * @return A new <code>String</code> decoded from the specified array of bytes using the given charset,
     * or <code>null</code> if the input byte array was <code>null</code>.
     * @throws IllegalStateException Thrown when a {@link UnsupportedEncodingException} is caught, which should never
     * happen for a
     *                               required charset name.
     * @see String#String(byte[], String)
     */
    public static String newString(final byte[] bytes, final String charsetName) {
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, charsetName);
        } catch (final UnsupportedEncodingException e) {
            throw StringUtil.newIllegalStateException(charsetName, e);
        }
    }

    /**
     * Constructs a new <code>String</code> by decoding the specified array of bytes using the ISO-8859-1 charset.
     *
     * @param bytes The bytes to be decoded into characters, may be <code>null</code>
     * @return A new <code>String</code> decoded from the specified array of bytes using the ISO-8859-1 charset, or
     * <code>null</code> if the input byte array was <code>null</code>.
     * @throws NullPointerException Thrown if {@link StandardCharsets#ISO_8859_1} is not initialized, which should
     * never happen since it is
     *                              required by the Java platform specification.
     * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
     */
    public static String newStringIso8859_1(final byte[] bytes) {
        return new String(bytes, StandardCharsets.ISO_8859_1);
    }

    /**
     * Constructs a new <code>String</code> by decoding the specified array of bytes using the US-ASCII charset.
     *
     * @param bytes The bytes to be decoded into characters
     * @return A new <code>String</code> decoded from the specified array of bytes using the US-ASCII charset,
     * or <code>null</code> if the input byte array was <code>null</code>.
     * @throws NullPointerException Thrown if {@link StandardCharsets#US_ASCII} is not initialized, which should
     * never happen since it is
     *                              required by the Java platform specification.
     * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
     */
    public static String newStringUsAscii(final byte[] bytes) {
        return new String(bytes, StandardCharsets.US_ASCII);
    }

    /**
     * Constructs a new <code>String</code> by decoding the specified array of bytes using the UTF-16 charset.
     *
     * @param bytes The bytes to be decoded into characters
     * @return A new <code>String</code> decoded from the specified array of bytes using the UTF-16 charset
     * or <code>null</code> if the input byte array was <code>null</code>.
     * @throws NullPointerException Thrown if {@link StandardCharsets#UTF_16} is not initialized, which should never
     * happen since it is
     *                              required by the Java platform specification.
     * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
     */
    public static String newStringUtf16(final byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_16);
    }

    /**
     * Constructs a new <code>String</code> by decoding the specified array of bytes using the UTF-16BE charset.
     *
     * @param bytes The bytes to be decoded into characters
     * @return A new <code>String</code> decoded from the specified array of bytes using the UTF-16BE charset,
     * or <code>null</code> if the input byte array was <code>null</code>.
     * @throws NullPointerException Thrown if {@link StandardCharsets#UTF_16BE} is not initialized, which should
     * never happen since it is
     *                              required by the Java platform specification.
     * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
     */
    public static String newStringUtf16Be(final byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_16BE);
    }

    /**
     * Constructs a new <code>String</code> by decoding the specified array of bytes using the UTF-16LE charset.
     *
     * @param bytes The bytes to be decoded into characters
     * @return A new <code>String</code> decoded from the specified array of bytes using the UTF-16LE charset,
     * or <code>null</code> if the input byte array was <code>null</code>.
     * @throws NullPointerException Thrown if {@link StandardCharsets#UTF_16LE} is not initialized, which should
     * never happen since it is
     *                              required by the Java platform specification.
     * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
     */
    public static String newStringUtf16Le(final byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_16LE);
    }

    /**
     * Constructs a new <code>String</code> by decoding the specified array of bytes using the UTF-8 charset.
     *
     * @param bytes The bytes to be decoded into characters
     * @return A new <code>String</code> decoded from the specified array of bytes using the UTF-8 charset,
     * or <code>null</code> if the input byte array was <code>null</code>.
     * @throws NullPointerException Thrown if {@link StandardCharsets#UTF_8} is not initialized, which should never
     * happen since it is
     *                              required by the Java platform specification.
     * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
     */
    public static String newStringUtf8(final byte[] bytes) {
        return newString(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 非空字符串
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static String notBlank(String value, String defaultValue) {
        return isEmpty(value) ? defaultValue : value;
    }

    public static String hiddenPhone(String phone) {
        return hiddenPhone(phone, 4);
    }

    /**
     * 隐藏手机号码中间号码
     *
     * @param phone 手机号
     * @param count 隐藏位数
     */
    public static String hiddenPhone(String phone, int count) {
        if (phone == null) {
            return phone;
        }
        return hiddenMiddle(phone, count);
    }

    /**
     * 隐藏左边字符
     *
     * @param orgStr 原始字符串
     * @param count  隐藏位数
     */
    public static String hiddenLeft(String orgStr, int count) {
        return hidden(orgStr, 0, count);
    }

    /**
     * 隐藏右边字符
     *
     * @param orgStr 原始字符串
     * @param count  隐藏位数
     */
    public static String hiddenRight(String orgStr, int count) {
        if (orgStr == null) {
            return orgStr;
        }
        return hidden(orgStr, orgStr.length() - count, orgStr.length());
    }

    /**
     * 隐藏中间几位字符
     *
     * @param orgStr 原始串
     * @param count  隐藏位数
     */
    public static String hiddenMiddle(String orgStr, int count) {
        if (orgStr == null) {
            return orgStr;
        }
        int len = orgStr.length();
        if (len < (count + 2)) {
            return orgStr;
        }
        int start = (len - count) / 2;
        int end = start + count;
        return hidden(orgStr, start, end);
    }

    /**
     * 字符串隐藏
     *
     * @param orgStr 原始串
     * @param start  起始位置(包含,下标从0开始)
     * @param end    结束位置(不包含)
     */
    public static String hidden(String orgStr, int start, int end) {
        if (orgStr == null) {
            return orgStr;
        }
        int len = orgStr.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (i >= start && i < end) {
                sb.append("*");
            } else {
                sb.append(orgStr.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 检测字符串中是否包含emoji或者其他非文字类型的字符
     *
     * @param source 需要判断的字符串
     * @return boolean
     */
    public static boolean checkEmoji(String source) {
        String regEx = "[^\\u0000-\\uFFFF]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(source);
        return m.find();
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source 需要过滤的字符串
     * @return String
     */
    public static String filterEmoji(String source) {
        source = source.replaceAll("[^\\u0000-\\uFFFF]", "");
        return source;
    }

    /**
     * 检测字符串中是否有特殊符号
     *
     * @param source
     * @return boolean
     */
    public static boolean checkSymbol(String source) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(source);
        return m.find();
    }

    /**
     * 过滤字符串中所有的特殊符号
     *
     * @param source 需要过滤的字符串
     * @return String
     */
    public static String filterSymbol(String source) {
        if (isEmpty(source)) {
            return "";
        }
        source = source.replaceAll(" ", "");
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(source);
        return m.replaceAll("").trim();
    }
}
