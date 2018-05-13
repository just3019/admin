package org.demon.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Description:
 * Created by Sean.xie on 2017/1/4.
 */

public class Charsets {
    /**
     * CharEncodingISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1.
     * <p/>
     * Every implementation of the Java platform is required to support this character encoding.
     *
     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     */
    public static final String ISO_8859_1 = StandardCharsets.ISO_8859_1.toString();

    /**
     * Seven-bit ASCII, also known as ISO646-US, also known as the Basic Latin block of the Unicode character set.
     * <p/>
     * Every implementation of the Java platform is required to support this character encoding.
     *
     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     */
    public static final String US_ASCII = StandardCharsets.US_ASCII.toString();

    /**
     * Sixteen-bit Unicode Transformation Format, The byte order specified by a mandatory initial byte-order mark
     * (either order accepted on input, big-endian used on output)
     * <p/>
     * Every implementation of the Java platform is required to support this character encoding.
     *
     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     */
    public static final String UTF_16 = StandardCharsets.UTF_16.toString();

    /**
     * Sixteen-bit Unicode Transformation Format, big-endian byte order.
     * <p/>
     * Every implementation of the Java platform is required to support this character encoding.
     *
     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     */
    public static final String UTF_16BE = StandardCharsets.UTF_16BE.toString();

    /**
     * Sixteen-bit Unicode Transformation Format, little-endian byte order.
     * <p/>
     * Every implementation of the Java platform is required to support this character encoding.
     *
     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     */
    public static final String UTF_16LE = StandardCharsets.UTF_16LE.toString();

    /**
     * Eight-bit Unicode Transformation Format.
     * <p/>
     * Every implementation of the Java platform is required to support this character encoding.
     *
     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     */
    public static final String UTF_8 = StandardCharsets.UTF_8.toString();

    /**
     * Returns the given Charset or the default Charset if the given Charset is null.
     *
     * @param charset A charset or null.
     * @return the given Charset or the default Charset if the given Charset is null
     */
    public static Charset toCharset(final Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    /**
     * Returns a Charset for the named charset. If the name is null, return the default Charset.
     *
     * @param charset The name of the requested charset, may be null.
     * @return a Charset for the named charset
     * @throws java.nio.charset.UnsupportedCharsetException If the named charset is unavailable
     */
    public static Charset toCharset(final String charset) {
        return charset == null ? Charset.defaultCharset() : Charset.forName(charset);
    }

}

