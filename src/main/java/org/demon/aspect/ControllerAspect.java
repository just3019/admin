package org.demon.aspect;

/**
 * Description:
 * Date: 2016/10/9 18:38
 *
 * @author Sean.xie
 */

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.demon.annotation.HtmlEscapeResponse;
import org.demon.annotation.WebResponse;
import org.demon.bean.BaseHeader;
import org.demon.bean.BaseResult;
import org.demon.code.ResultCodes;
import org.demon.config.DefaultExceptionHandler;
import org.demon.exception.CodeException;
import org.demon.filter.RequestFilter;
import org.demon.request.RequestHolder;
import org.demon.util.Logger;
import org.demon.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Controller 切面
 * 处理所有Controller 请求, 将返回结构统一
 */
@Component
@Aspect
public class ControllerAspect {
    /**
     * 响应类型
     */
    public static final ThreadLocal<ResponseType> RESPONSE_TYPES = new ThreadLocal<>();
    private Logger logger = Logger.newInstance(getClass());

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void cutController() {
    }

    /**
     * 切点
     */
    @Around("cutController()")
    public Object cutPoint(ProceedingJoinPoint point) {
        RESPONSE_TYPES.remove();
        try {
            StringBuilder log = RequestFilter.getLogRecorder(false);
            log.append(StringUtil.formatContent("\nController request body : {0}", AspectUtil.getStringArgs(point)));
            // 处理WEB 端数据(html 需要特殊处理)
            ResponseType responseType = RESPONSE_TYPES.get();
            if (responseType == null) {
                responseType = new ResponseType();
                RESPONSE_TYPES.set(responseType);
            }
            // 是否转义html
            responseType.htmlEscape = false;
            responseType.safeHtml = false;
            // 是否进行URL DECODE
            responseType.urlDecode = false;
            WebResponse webResponse = AspectUtil.getTargetAnnotation(point, WebResponse.class);
            if (webResponse != null) {
                responseType.urlDecode = true;
                Signature sign = point.getSignature();
                if (sign instanceof MethodSignature) {
                    HtmlEscapeResponse htmlEscapeResponse = AspectUtil.getMethodAnnotation(point, HtmlEscapeResponse
                            .class);
                    if (htmlEscapeResponse != null) {
                        responseType.htmlEscape = true;
                        responseType.safeHtml = htmlEscapeResponse.safeHtml();
                    }
                }
            }
            /**
             * 调用发需要对数据进行URLEncode并处理
             */
            BaseHeader headers = RequestHolder.getHeaderParams();
            if (headers != null) {
                if (headers.respDecode) {
                    responseType.urlDecode = true;
                }
                if (headers.htmlEscape) {
                    responseType.htmlEscape = true;
                }
                if (headers.htmlSafe) {
                    responseType.safeHtml = true;
                }
                if (headers.fullJson) {
                    responseType.fullJson = true;
                }
            }

            BaseResult<Object> result = new BaseResult<>();
            CodeException.ExceptionInfo code = ResultCodes.Code.COMMON_SUCCESS;
            Object resultObject = point.proceed();
            if (resultObject instanceof List) { // List类型对应list
                result.setList((List) resultObject);
            } else if (resultObject instanceof Set) { //Set类型对应list
                result.setList(Arrays.asList(((Set) resultObject).toArray()));
            } else {
                result.setData(resultObject);
            }
            result.setState(code.getCode());
            result.setErrormsg(code.getMsg());
            log.append(StringUtil.formatContent("\nController response body : {0}", result.toString()));
            return result;
        } catch (Throwable e) {
            return DefaultExceptionHandler.handleException(e);
        }
    }

    /**
     * 请求头信息
     */
    public static class RequestHeader {
        // 请求序列
        public String reqSeq;
        // 请求ip
        public String ip;
        // 请求用户
        public Long uid;
    }

    /**
     * Description: 返回数据处理类型
     * Created by Sean.xie on 2017/3/1.
     */
    public static class ResponseType {
        /**
         * 是否进行html 转义
         */
        public boolean htmlEscape;
        /**
         * 安全HTML
         */
        public boolean safeHtml;
        /**
         * 是否进行URL decode
         */
        public boolean urlDecode;
        /**
         * Json show all fields
         */
        public boolean fullJson;
    }
}