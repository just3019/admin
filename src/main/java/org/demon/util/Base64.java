/*
 * Copyright (C) 2010 The MobileSecurePay Project All right reserved. author:
 * shiqun.shi@alipay.com
 */

package org.demon.util;

public final class Base64 {

    private static final java.util.Base64.Encoder BASE64_ENCODER = java.util.Base64.getEncoder();
    private static final java.util.Base64.Decoder BASE64_DECODER = java.util.Base64.getDecoder();

    /**
     * Encodes String into Base64
     */
    public static String encode(String value) {
        if (StringUtil.isEmpty(value)) {
            return "";
        }
        return encode(value.getBytes());
    }

    /**
     * Encodes hex octects into Base64
     *
     * @param binaryData Array containing binaryData
     * @return Encoded Base64 array
     */
    public static String encode(byte[] binaryData) {
        if (binaryData == null) {
            return null;
        }
        return new String(BASE64_ENCODER.encode(binaryData));
    }

    /**
     * Decodes Base64 data into String
     */
    public static String decode2Sting(String encoded) {
        byte[] bytes = decode(encoded);
        return bytes == null ? null : new String(bytes);
    }

    /**
     * Decodes Base64 data into octects
     *
     * @param encoded string containing Base64 data
     * @return Array containind decoded data.
     */
    public static byte[] decode(String encoded) {
        if (encoded == null) {
            return null;
        }
        if (encoded.contains("=")) {
            encoded = encoded.substring(0, encoded.indexOf("="));
        }
        return BASE64_DECODER.decode(encoded);
    }

}
