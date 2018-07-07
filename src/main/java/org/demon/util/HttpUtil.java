package org.demon.util;


import org.demon.exception.net.ParseBeanException;
import org.demon.exception.net.ServerIOException;
import org.demon.exception.net.ServerOtherException;
import org.demon.exception.net.ServerProtocolException;
import org.demon.exception.net.ServerTimeoutException;
import org.demon.exception.net.ServerUrlException;
import org.demon.exception.net.ServerUrlNotFoundException;
import org.demon.parse.InputStreamParser;
import org.demon.parse.impl.FileInputParser;
import org.demon.parse.impl.StringInputParser;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;


/**
 * Http 工具类
 *
 * @author Xielei
 */
public final class HttpUtil {
    private static final String GZIP_ENCODING = "gzip";
    private static final int TIME_OUT = 60 * 1000;
    /* valid HTTP methods */
    private static final String[] methods = {
            "GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"
    };
    private static Logger logger = Logger.newInstance(HttpUtil.class);

    public static <T> T upload(String url, byte[] data, InputStreamParser<T> parser)
            throws ServerIOException, ServerTimeoutException, ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(true, url, null, data, "multipart/form-data;boundary=******", parser);
    }

    public static <T> T upload(String url, String filePath, InputStreamParser<T> parser)
            throws ServerIOException, ServerTimeoutException, ServerProtocolException,
            ServerOtherException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(true, url, null, null, filePath, "multipart/form-data;boundary=******",
                parser);
    }

    /**
     * HTTP GET 请求 下载网页 图片 等
     *
     * @param url
     * @param parser
     */
    public static <T> T loadGetRequest(String url, InputStreamParser<T> parser)
            throws ServerIOException, ServerTimeoutException, ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(false, url, null, null, "", parser);
    }

    public static <T> T loadGetRequest(String url, Map<String, String> headers, InputStreamParser<T> parser)
            throws ServerIOException, ServerTimeoutException, ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(false, url, headers, null, "", parser);
    }

    public static String loadGetRequest(String url) throws ServerIOException, ServerTimeoutException,
            ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(false, url, null, null, null, new StringInputParser());
    }

    /**
     * 下载文件
     *
     * @param url
     * @param file
     * @return
     * @throws ServerIOException
     * @throws ServerTimeoutException
     * @throws ServerOtherException
     * @throws ServerProtocolException
     * @throws ServerUrlException
     * @throws ParseBeanException
     * @throws ServerUrlNotFoundException
     */
    public static boolean downloadFile(String url, String file) throws ServerIOException, ServerTimeoutException,
            ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(false, url, null, null, null, new FileInputParser(file));
    }

    /**
     * HTTP Post 请求
     */
    public static <T> T loadPostRequest4JSON(String url, byte[] params, InputStreamParser<T> parser)
            throws ServerIOException, ServerTimeoutException, ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(true, url, null, params, "application/json; charset=utf-8", parser);
    }

    public static <T> T loadPostRequest4JSONInForm(String url, Map<String, String> params, InputStreamParser<T> parser)
            throws ServerIOException, ServerTimeoutException, ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadPostRequest4JSONInForm(url, null, params, parser);
    }

    public static <T> T loadPostRequest4JSONInForm(String url, Map<String, String> headers, Map<String, String>
            params, InputStreamParser<T> parser)
            throws ServerIOException, ServerTimeoutException, ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        if (params == null) {
            logger.warn("param is null");
            return null;
        }
        StringBuilder sb = new StringBuilder("1=1");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append("&").append(entry.getKey()).append("=")
                    .append(entry.getValue());
        }
        return loadData(true, url, headers, sb.toString().getBytes(),
                "application/x-www-form-urlencoded; charset=UTF-8", parser);
    }

    public static String loadPostRequest(String url, byte[] params) throws ServerIOException, ServerTimeoutException,
            ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadPostRequest(url, params, null);
    }

    public static <T> T loadPostRequestInForm(String url, Map<String, Object> params, InputStreamParser<T> parser)
            throws ServerIOException, ServerTimeoutException, ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadPostRequestInForm(url, null, params, parser);
    }

    public static <T> T loadPostRequestInForm(String url, Map<String, String> headers, Map<String, Object> params,
                                              InputStreamParser<T> parser) throws ServerIOException,
            ServerTimeoutException, ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        if (params == null) {
            logger.warn("param is null");
            return null;
        }
        StringBuilder sb = new StringBuilder("1=1");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append("&").append(entry.getKey()).append("=")
                    .append(entry.getValue());
        }
        return loadData(true, url, headers, sb.toString().getBytes(),
                "application/x-www-form-urlencoded; charset=UTF-8", parser);
    }

    public static String loadPostRequest(String url, byte[] params, String contentType) throws ServerIOException,
            ServerTimeoutException, ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(true, url, null, params, contentType, new StringInputParser());
    }

    /**
     * HTTP Post 请求
     */
    public static <T> T loadPostRequest4JSON(String url, Map<String, String> headers,
                                             byte[] params, InputStreamParser<T> parser)
            throws ServerIOException, ServerTimeoutException, ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(true, url, headers, params, "application/json; charset=UTF-8", parser);
    }

    /**
     * HTTP 请求
     */
    public static <T> T loadData4JSON(boolean isPost, String url, Map<String, String> headers,
                                      byte[] params, InputStreamParser<T> parser)
            throws ServerIOException, ServerTimeoutException, ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(isPost, url, headers, params, "application/json; charset=utf-8", parser);
    }

    /**
     * HTTP 请求
     *
     * @return
     */
    public static <T> T loadData4XML(boolean isPost, String url, Map<String, String> headers,
                                     byte[] params, InputStreamParser<T> parser)
            throws ServerIOException, ServerTimeoutException, ServerOtherException,
            ServerProtocolException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(isPost, url, headers, params, "application/xml; charset=utf-8", parser);
    }

    /**
     * 加载数据
     *
     * @return
     */
    public static <T> T loadData(boolean isPost, String url, Map<String, String> headers,
                                 byte[] params, String contentType, InputStreamParser<T> parser)
            throws ServerIOException, ServerTimeoutException, ServerProtocolException,
            ServerOtherException, ServerUrlException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(isPost, url, headers, params, null, contentType, parser);
    }

    /**
     * 加载数据
     *
     * @param isPost
     * @param url
     * @param headers
     * @param params
     * @param filePath
     * @param contentType
     * @param parser
     * @param <T>
     * @return
     */
    public static <T> T loadData(boolean isPost, String url, Map<String, String> headers,
                                 byte[] params, String filePath, String contentType, InputStreamParser<T> parser)
            throws ServerTimeoutException, ServerUrlException, ServerProtocolException,
            ServerIOException, ServerOtherException, ParseBeanException, ServerUrlNotFoundException {
        return loadData(isPost, url, headers, params, filePath, contentType, parser, null);
    }

    public static <T> T loadData(boolean isPost, String url, Map<String, String> headers,
                                 byte[] params, String filePath, String contentType, InputStreamParser<T> parser,
                                 Integer timeout)
            throws ServerTimeoutException, ServerUrlException, ServerProtocolException,
            ServerIOException, ServerOtherException, ParseBeanException, ServerUrlNotFoundException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        T t = null;
        try {
            logger.info("loadData org_url:{}", url);
            URL httpurl = new URL(UrlUtil.escapeAllChineseChar(UrlUtil.resolvValidUrl(url)));
            logger.info("loadData httpurl:{}", url);
            conn = (HttpURLConnection) httpurl.openConnection();
            conn.setConnectTimeout(timeout == null ? TIME_OUT : timeout);
            conn.setReadTimeout(timeout == null ? TIME_OUT : timeout);

            if (isPost) {
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
            } else {
                conn.setRequestMethod("GET");
            }
            conn.setUseCaches(false);
            conn.addRequestProperty("Accept-Encoding", "gzip");
            conn.addRequestProperty("Accept-Encoding", "compress");
            conn.addRequestProperty("charset", "utf-8");
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    if (!StringUtil.isEmpty(entry.getValue())) {
                        conn.addRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                logger.info("loadData headers: " + JSONUtil.obj2Json(headers));
            }
            if (!StringUtil.isEmpty(contentType)) {
                conn.setRequestProperty("Content-Type", contentType);
            }
            if (params != null) {
                conn.setRequestProperty("Content-Length", String.valueOf(params.length));
            }
            conn.setRequestProperty("Connection", "keep-alive");

            if (isPost) {
                out = conn.getOutputStream();
                if (!StringUtil.isEmpty(filePath)) {
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(filePath);
                        int len = -1;
                        byte[] bytes = new byte[1024 * 8];
                        while ((len = fis.read(bytes)) != -1) {
                            out.write(bytes, 0, len);
                        }
                    } finally {
                        try {
                            fis.close();
                        } catch (Exception e) {
                            logger.printStackTrace(e);
                        }
                    }
                } else if (params != null) {
                    out.write(params);
                }
                out.flush();
            }
            if (conn.getResponseCode() == 200) {
                if (!StringUtil.isEmpty(conn.getContentEncoding())
                        && conn.getContentEncoding().contains(GZIP_ENCODING)) {
                    t = parser.parser(new GZIPInputStream(conn.getInputStream()));
                } else {
                    t = parser.parser(conn.getInputStream());
                }
                logger.debug("Response Result: url:{}, req : {}, resp : {}", httpurl, params == null ? "" : new
                        String(params), JSONUtil.obj2Json(t));
            } else {
                InputStream input = null;
                try {
                    input = conn.getErrorStream();
                    if (input != null) {
                        int len = -1;
                        byte[] bytes = new byte[1024 * 4];
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        while ((len = input.read(bytes)) != -1) {
                            bos.write(bytes, 0, len);
                        }
                        bos.close();
                        logger.warn("HttpUtil: Error Message {} \n req:{}", bos.toString(), params == null ? "" : new
                                String(params));
                    }
                } catch (Exception e) {
                    logger.printStackTrace(e);
                } finally {
                    if (input != null) {
                        input.close();
                    }
                }
                String msg = "Response Result : url:" + url + " response Code:" + conn.getResponseCode() + " msg:" +
                        conn.getResponseMessage();
                logger.warn(msg);
                throw new ServerUrlNotFoundException(msg);
            }
        } catch (ConnectException e) {
            logger.info("connecte error - url:{},param:{}", url, params == null ? "" : new String(params));
            logger.printStackTrace(e);
            throw new ServerOtherException(e);
        } catch (SocketTimeoutException e) {
            logger.printStackTrace(e);
            throw new ServerTimeoutException(e);
        } catch (MalformedURLException e) {
            logger.printStackTrace(e);
            throw new ServerUrlException(e);
        } catch (ProtocolException e) {
            logger.printStackTrace(e);
            throw new ServerProtocolException(e);
        } catch (IOException e) {
            logger.printStackTrace(e);
            throw new ServerIOException(e);
        } catch (ServerUrlNotFoundException e) {
            logger.printStackTrace(e);
            throw e;
        } catch (ParseBeanException e) {
            logger.printStackTrace(e);
            throw e;
        } catch (Exception e) {
            logger.printStackTrace(e);
            throw new ServerOtherException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return t;
    }

}
