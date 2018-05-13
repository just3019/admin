package org.demon.request;

import org.demon.bean.BaseHeader;
import org.demon.code.ResultCodes;
import org.demon.exception.CodeException;
import org.demon.init.ApplicationEnvConfig;
import org.demon.util.JSONUtil;
import org.demon.util.NumberUtil;
import org.demon.util.StringUtil;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.context.request.FacesRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Description: RequestHolder
 * 统一管理 request Attributes Headers
 * Created by Sean.xie on 2017/3/18.
 */
public class RequestHolder extends RequestContextHolder {
    /**
     * sessionId
     */
    public static final String HEADER_REQUEST_SESSION_ID = "sessionId";
    /**
     * 请求序列
     */
    public static final String HEADER_REQUEST_SEQUENCE = "reqSeq";
    /**
     * 请求序列层级
     */
    public static final String HEADER_REQUEST_SEQUENCE_LAYER = "reqSeqLayer";
    /**
     * JWT
     */
    public static final String JWT = "jwt";
    /**
     * 请求API
     */
    public static final String HEADER_REQUEST_API = "reqApi";
    /**
     * 原始请求API
     */
    public static final String HEADER_ORIGIN_REQUEST_API = "originReqApi";
    /**
     * 原始请求Host
     */
    public static final String HEADER_ORIGIN_HOST = "originHost";
    /**
     * App版本号
     */
    public static final String HEADER_APP_VER = "appVer";
    /**
     * App类型
     */
    public static final String HEADER_APP_TYPE = "appType";
    /**
     * 附加数据
     */
    public static final String HEADER_EXT_DATA = "extData";
    /**
     * 统计UV
     */
    public static final String HEADER_TARGET_ID = "targetID";
    // -------------- 灰度发布参数 Start ------
    /**
     * 灰度部署
     */
    public static final String HEADER_REQUEST_AB_PUBLISH = "ab_publish";
    /**
     * Header Name targetIp
     */
    public static final String HEADER_TARGET_IP = "targetIp";
    /**
     * Header Name deviceIp
     */
    public static final String HEADER_DEVICE_IP = "deviceIp";
    /**
     * Header Name targetUid
     */
    public static final String HEADER_TARGET_UID = "targetUid";
    /**
     * Header Name deviceId
     */
    public static final String HEADER_DEVICE_ID = "deviceId";
    // -------------- 灰度发布参数 End ------
    // -------------- 响应数据参数 Start ------
    /**
     * Header Name respDecode
     */
    private static final String HEADER_RESP_DECODE = "respUrlDecode";
    /**
     * Header Name htmlSafe
     */
    private static final String HEADER_RESP_SAFE = "htmlSafe";
    /**
     * Header Name htmlSafe
     */
    private static final String HEADER_RESP_HTMLESCAPE = "htmlEscape";
    // -------------- 响应数据参数 End ------
    // -------------- JWT属性 Start ------
    /**
     * USER
     */
    private static final String USER = "user";
    /**
     * uid
     */
    private static final String UID = "uid";
    // -------------- JWT属性 End ------

    private static final ThreadLocal<Map<String, String>> REQUEST_HEADERS = new NamedThreadLocal<>("Request headers");
    private static final ThreadLocal<Map<String, String>> REQUEST_HOLDER = new NamedThreadLocal<>("Request Attribute");

    private static JWT.Clock clock;

    private static LinkedCaseInsensitiveMap<String> FILTER_HEADERS = new LinkedCaseInsensitiveMap<>();

    static {
        FILTER_HEADERS.put(HttpHeaders.HOST, null);
        FILTER_HEADERS.put(HttpHeaders.CONTENT_TYPE, null);
        FILTER_HEADERS.put(HttpHeaders.CONTENT_LENGTH, null);
        FILTER_HEADERS.put(HttpHeaders.TRANSFER_ENCODING, null);
        FILTER_HEADERS.put(HEADER_RESP_DECODE, null);
        FILTER_HEADERS.put(HEADER_RESP_SAFE, null);
        FILTER_HEADERS.put(HEADER_RESP_HTMLESCAPE, null);
    }

    public static void setClock(JWT.Clock clock) {
        RequestHolder.clock = clock;
    }

    /**
     * Reset the Request Headers for the current thread.
     */
    public static void resetRequestHolder() {
        resetRequestAttributes();
        Map<String, String> map = REQUEST_HOLDER.get();
        if (map != null) {
            map.clear();
        }
        REQUEST_HOLDER.remove();
        map = REQUEST_HEADERS.get();
        if (map != null) {
            map.clear();
        }
        REQUEST_HEADERS.remove();
    }

    /**
     * 初始化
     */
    private static Map<String, String> getHolder() {
        Map<String, String> map = REQUEST_HOLDER.get();
        if (map == null) {
            synchronized (RequestHolder.class) {
                map = new HashMap<>();
                REQUEST_HOLDER.set(map);
            }
        }
        return map;
    }

    /**
     * set attribute
     *
     * @param key   key
     * @param value value
     */
    public static void setAttribute(String key, String value) {
        getHolder().put(key, value);
    }

    public static void setJwt(String jwt) {
        setAttribute(JWT, jwt);
    }

    public static void resetJwt() {
        setAttribute(JWT, null);
        setAttribute(UID, null);
        setAttribute(USER, null);
    }

    public static JWT getJWT() {
        JWT token = new JWT(ApplicationEnvConfig.JWT_KEY).getJWT(getAttribute(JWT));
        return token;
    }

    /**
     * 请求头中的jwt中 取 Uid
     */
    public static int getUid() {
        return getUid(false);
    }

    /**
     * 请求头中的jwt中 取 Uid
     */
    public static int getUidWithException() {
        return getUid(true);
    }

    /**
     * 请求头中的jwt中 取 Uid
     */
    private static int getUid(boolean throwException) {
        String uid = getAttribute(UID);
        if (uid != null) {
            int id = NumberUtil.getInteger(uid, 0);
            // 不需要抛异常时,直接返回,需要抛异常继续执行后续处理
            if (!throwException) {
                return id;
            }
        }

        JWT jwt = getJWT();
        // jwt 不合法
        if (jwt == null || jwt.payload == null) {
            if (throwException) {
                throw new CodeException(ResultCodes.Code.COMMON_ERROR_LOGINED_TIMEOUT);
            }
            uid = "0";
        } else {
            long current;
            if (clock != null) {
                current = clock.currentTime();
            } else {
                current = System.currentTimeMillis();
            }
            long exp = jwt.payload.exp;
            // 过期处理
            if (current > exp) {
                if (throwException) {
                    throw new CodeException(ResultCodes.Code.COMMON_ERROR_LOGINED_TIMEOUT);
                }
                uid = "0";
            } else { // 未过期处理
                uid = jwt.payload.uid;
            }

        }
        setAttribute(UID, uid);
        return NumberUtil.getInteger(uid, 0);
    }

    /**
     * get attribute
     *
     * @param key key
     */
    public static String getAttribute(String key) {
        Map<String, String> attributes = getHolder();
        if (attributes == null) {
            return null;
        }
        return attributes.get(key);
    }


    /**
     * Bind the given Request Headers to the current thread.
     *
     * @param headers     the Request Headers to expose,
     *                    or {@code null} to reset the thread-bound context
     * @param inheritable whether to expose the Request Headers as inheritable
     *                    for child threads (using an {@link InheritableThreadLocal})
     */
    public static void setRequestHeaders(Map<String, String> headers, boolean inheritable) {
        if (headers != null) {
            REQUEST_HEADERS.set(headers);
        }
    }

    /**
     * Return the Request Headers currently bound to the thread.
     *
     * @return the Request Headers currently bound to the thread,
     * or {@code null} if none bound
     */
    public static Map<String, String> getRequestHeaders() {
        Map<String, String> headers = REQUEST_HEADERS.get();
        if (headers == null) {
            synchronized (RequestHolder.class) {
                headers = new HashMap<>();
                REQUEST_HEADERS.set(headers);
            }
        }
        return headers;
    }

    /**
     * Bind the given Request Headers to the current thread,
     * <i>not</i> exposing it as inheritable for child threads.
     *
     * @param headers the Request Headers to expose
     * @see #setRequestHeaders(Map<String,Object>, boolean)
     */
    public static void setRequestHeaders(Map<String, String> headers) {
        setRequestHeaders(headers, true);
    }

    /**
     * 过滤Header
     */
    public static Map<String, String> getRequestFilterHeaders() {
        Map<String, String> headers = new HashMap<>();
        getRequestHeaders().entrySet().stream()
                .filter(header -> !FILTER_HEADERS.containsKey(header.getKey()))
                .forEach(header -> headers.put(header.getKey(), header.getValue()));
        return headers;
    }

    /**
     * 过滤Header
     */
    public static Map<String, Collection<String>> getRequestFilterCollectionHeaders() {
        Map<String, Collection<String>> headers = new HashMap<>();
        getRequestHeaders().entrySet().stream()
                .filter(header -> !FILTER_HEADERS.containsKey(header.getKey()))
                .forEach(entry -> {
                    Collection<String> set = headers.computeIfAbsent(entry.getKey(), k -> new HashSet<>());
                    set.add(entry.getValue());
                });
        return headers;
    }

    /**
     * Return the Request Headers currently bound to the thread.
     * <p>Exposes the previously bound Request Headers instance, if any.
     * Falls back to the current JSF FacesContext, if any.
     *
     * @return the Request Headers currently bound to the thread
     * @throws IllegalStateException if no Request Headers object
     *                               is bound to the current thread
     * @see #setRequestAttributes
     * @see ServletRequestAttributes
     * @see FacesRequestAttributes
     */
    public static Map<String, String> currentRequestHeaders() throws IllegalStateException {
        Map<String, String> headers = getHeaders();
        if (headers == null) {
            throw new IllegalStateException("No thread-bound request found: "
                    + "Are you referring to request headers outside of an actual web request, "
                    + "or processing a request outside of the originally receiving thread? "
                    + "If you are actually operating within a web request and still receive this message, "
                    + "your code is probably running outside of DispatcherServlet/DispatcherPortlet: "
                    + "In this case, use RequestContextListener or "
                    + "RequestContextFilter to expose the current request.");
        }
        return headers;
    }

    /**
     * 请求序列
     */
    public static String getHeaderRequestSequence() {
        return getHeaders().get(HEADER_REQUEST_SEQUENCE);
    }

    /**
     * 请求序列层级
     */
    public static String getHeaderRequestSequenceLayer() {
        return getHeaders().get(HEADER_REQUEST_SEQUENCE_LAYER);
    }

    /**
     * 请求API
     */
    public static String getHeaderRequestApi() {
        return getHeaders().get(HEADER_REQUEST_API);
    }

    /**
     * 请求原始API
     */
    public static String getHeaderOriginRequestApi() {
        return getHeaders().get(HEADER_ORIGIN_REQUEST_API);
    }

    /**
     * 请求SessionId
     */
    public static String getHeaderSessionId() {
        return getHeaders().get(HEADER_REQUEST_SESSION_ID);
    }

    /**
     * 获取UID
     */
    public static Long getHeaderTargetUid() {
        return NumberUtil.getLong(getHeaders().get(HEADER_TARGET_UID));
    }

    /**
     * 获取调用设备IP
     */
    public static String getHeaderDeviceIp() {
        return getHeaders().get(HEADER_DEVICE_IP);
    }

    /**
     * 获取调用者IP
     */
    public static String getHeaderTargetIp() {
        return getHeaders().get(HEADER_TARGET_IP);
    }

    /**
     * UV
     */
    public static String getHeaderTargetID() {
        return getHeaders().get(HEADER_TARGET_ID);
    }

    /**
     * 获取APP版本号
     */
    public static String getHeaderAppVersion() {
        return getHeaders().get(HEADER_APP_VER);
    }

    /**
     * 获取APP 类型
     */
    public static String getHeaderAppType() {
        return getHeaders().get(HEADER_APP_TYPE);
    }

    /**
     * 获取附加数据
     */
    public static String getHeaderExtData() {
        return getHeaders().get(HEADER_EXT_DATA);
    }

    /**
     * 获取设备编号
     */
    public static String getHeaderDeviceId() {
        return getHeaders().get(HEADER_DEVICE_ID);
    }

    /**
     * 获取请求头
     */
    private static Map<String, String> getHeaders() {
        return getRequestHeaders();
    }

    /**
     * Request Headers
     */
    public static BaseHeader getHeaderParams() {
        Map<String, String> headers = getRequestHeaders();
        if (!StringUtil.isEmpty(headers)) {
            return JSONUtil.map2Obj(headers, BaseHeader.class);
        }
        return null;
    }
}
