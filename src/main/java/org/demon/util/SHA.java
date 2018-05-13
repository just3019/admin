package org.demon.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * SHA 算法编码
 */
public final class SHA {

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * SHA 算法编码
     */
    public static String getSHA(String value) {
        try {
            return new String(encodeHex(digest(value, "SHA", "UTF-8"), false));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * SHA1 算法编码
     */
    public static String getSHA1(String value) {
        try {
            return new String(encodeHex(digest(value, "SHA-1", "UTF-8"), false));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * SHA256 算法编码
     */
    public static String getSHA256Hex(String value) {
        return DigestUtils.sha256Hex(value);
    }

    /**
     * HMAC SHA256 算法编码
     */
    public static String getHMACSHA256(String data, byte[] key) {
        return getHMACSHA256(data.getBytes(), key);
    }

    /**
     * HMAC SHA256 算法编码
     */
    public static String getHMACSHA256(byte[] data, byte[] key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            return encodeHex(mac.doFinal(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 编码
     */
    private static byte[] digest(String value, String type, String charSet) throws Exception {
        MessageDigest md = MessageDigest.getInstance(type);
        md.update(value.getBytes(charSet));
        return md.digest();
    }

    /**
     * 转换16进制
     */
    private static String encodeHex(byte[] data) {
        return new String(encodeHex(data, false));
    }

    /**
     * 转换16进制
     */
    private static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * 转换16进制
     */
    private static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }
}
