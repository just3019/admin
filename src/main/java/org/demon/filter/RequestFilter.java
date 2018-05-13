package org.demon.filter;


import org.demon.Constants;
import org.demon.aspect.ControllerAspect;
import org.demon.request.RequestHolder;
import org.demon.util.Charsets;
import org.demon.util.Logger;
import org.demon.util.MD5;
import org.demon.util.NumberUtil;
import org.demon.util.StringUtil;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.demon.request.RequestHolder.JWT;


/**
 * Description:
 * Created by Sean.xie on 2017/4/28.
 */
@WebFilter(urlPatterns = "/*", filterName = "arequestFilter")
public class RequestFilter implements Filter {
    // 请求响应日志
    public static final ThreadLocal<StringBuilder> logRecorder = new ThreadLocal<>();
    //
    private static final String REQUEST_IP = RequestHolder.HEADER_TARGET_IP;
    private static final String REQUEST_ID = RequestHolder.HEADER_TARGET_ID;
    private static final String REQUEST_SEQUENCE = RequestHolder.HEADER_REQUEST_SEQUENCE;
    private static final String REQUEST_SEQUENCE_LAYER = RequestHolder.HEADER_REQUEST_SEQUENCE_LAYER;
    private static final String REQUEST_API = RequestHolder.HEADER_REQUEST_API;
    private static final String IP_UNKNOWN = Constants.UNKNOWN;
    private Logger logger = Logger.newInstance(RequestFilter.class);

    /**
     * 取日志记录器
     */
    public static StringBuilder getLogRecorder(boolean isDelete) {
        StringBuilder recorder = logRecorder.get();
        if (recorder == null) {
            recorder = new StringBuilder();
            logRecorder.set(recorder);
        }
        if (isDelete) {
            recorder.delete(0, recorder.length());
        }
        return recorder;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        StringBuilder log = getLogRecorder(true);
        RequestHolder.resetRequestHolder();
        long cur = System.currentTimeMillis();
        filter(log, servletRequest, servletResponse, filterChain);
        long sbu = (System.currentTimeMillis() - cur);
        if (sbu > 2 * 1000) {
            log.append(StringUtil.formatContent("\n[严重警告]RequestFilter 耗时: 毫秒({0}) , 秒({1})", sbu, sbu / 1000f));
        } else {
            log.append(StringUtil.formatContent("\nRequestFilter 耗时: 毫秒({0}) , 秒({1})", sbu, sbu / 1000f));
        }
        logger.info(log.toString());
        RequestHolder.resetRequestHolder();
        ControllerAspect.RESPONSE_TYPES.remove();
        logRecorder.remove();
    }

    private void filter(StringBuilder log, ServletRequest servletRequest, ServletResponse servletResponse,
                        FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        RequestHolder.setJwt(request.getHeader(JWT));
        preHandle(log, request);
        setRequestCharset(request);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        setResponseCharset(response);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 打印 Header
     *
     * @param headers 请求头
     */
    private void printHeaders(StringBuilder log, Map<String, String> headers) {
        Optional.ofNullable(headers).orElse(new HashMap<>())
                .forEach((k, v) -> log.append(StringUtil.formatContent("\nheader : {0}={1}", k, v)));
    }

    @Override
    public void destroy() {
    }

    public void preHandle(StringBuilder log, HttpServletRequest request) {
        String api = request.getRequestURI();
        Map<String, String> headers = new LinkedCaseInsensitiveMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        String value;
        String key;
        while (headerNames.hasMoreElements()) {
            key = headerNames.nextElement();
            value = request.getHeader(key);
            headers.put(key, value);
        }
        String ip = request.getHeader(REQUEST_IP);
        if (StringUtil.isEmpty(ip)) {
            ip = getIpAddress(request);
            // 取IP
            headers.put(REQUEST_IP, ip);
        }
        String id = request.getHeader(REQUEST_ID);
        if (StringUtil.isEmpty(id)) {
            // 取ID
            headers.put(REQUEST_ID, getAccessID(request, ip));
        }
        String seq = request.getHeader(REQUEST_SEQUENCE);
        if (StringUtil.isEmpty(seq)) {
            seq = MD5.md5(UUID.randomUUID().toString());
            headers.put(REQUEST_SEQUENCE, seq);
        }
        Integer seqLayer = NumberUtil.getInteger(request.getHeader(REQUEST_SEQUENCE_LAYER));
        if (seqLayer == null) {
            seqLayer = 1;
        } else {
            seqLayer += 1;
        }
        headers.put(REQUEST_SEQUENCE_LAYER, String.valueOf(seqLayer));
        headers.put(REQUEST_API, api);
        RequestHolder.setRequestHeaders(headers);
        logger.info("RequestFilter api : {}", api);
        log.append(StringUtil.formatContent("\nRequestFilter api : {0}\n"
                + "query : {1}", api, StringUtil.transNull(request.getQueryString())));
        printHeaders(log, headers);
    }

    /**
     * UV
     *
     * @param request 请求
     * @param ip      ip
     * @return 返回标示ID
     */
    private String getAccessID(HttpServletRequest request, String ip) {
        String accept = request.getHeader("accept");
        String agent = request.getHeader("user-agent");
        String acceptEncoding = request.getHeader("accept-encoding");
        return MD5.toMD5String(accept + agent + acceptEncoding + ip);
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     */
    public String getIpAddress(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");
        logger.debug("getIpAddress - X-Forwarded-For - String ip=" + ip);
        if (isEmptyIp(ip)) {
            if (isEmptyIp(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                logger.debug("getIpAddress - Proxy-Client-IP - String ip=" + ip);
            }
            if (isEmptyIp(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                logger.debug("getIpAddress - WL-Proxy-Client-IP - String ip=" + ip);
            }
            if (isEmptyIp(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                logger.debug("getIpAddress - HTTP_CLIENT_IP - String ip=" + ip);
            }
            if (isEmptyIp(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                logger.debug("getIpAddress - HTTP_X_FORWARDED_FOR - String ip=" + ip);
            }
            if (isEmptyIp(ip)) {
                ip = request.getHeader("x-real-ip");
                logger.debug("getIpAddress - x-real-ip - String ip=" + ip);
            }
            if (isEmptyIp(ip)) {
                ip = request.getRemoteAddr();
                logger.debug("getIpAddress - getRemoteAddr - String ip=" + ip);
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!(IP_UNKNOWN.equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }

        if (StringUtil.isEmpty(ip)) {
            ip = IP_UNKNOWN;
        }
        return ip;
    }

    private boolean isEmptyIp(String ip) {
        return StringUtil.isEmpty(ip) || IP_UNKNOWN.equalsIgnoreCase(ip);
    }

    public void setRequestCharset(HttpServletRequest httpServletRequest) {
        if (httpServletRequest != null) {
            try {
                httpServletRequest.setCharacterEncoding(Charsets.UTF_8);
            } catch (Exception e) {
                logger.printStackTrace(e);
            }
        }
    }

    public void setResponseCharset(HttpServletResponse response) {
        response.setCharacterEncoding(Charsets.UTF_8);
        response.setContentType("application/json;charset=UTF-8");
    }
}