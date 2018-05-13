package org.demon.config;

import org.demon.bean.BaseResult;
import org.demon.code.ResultCodes;
import org.demon.exception.CodeException;
import org.demon.init.ApplicationEnvConfig;
import org.demon.util.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Description: 异常统一处理类
 * Date: 2016/10/28 12:28
 *
 * @author Sean.xie
 */
@ControllerAdvice
public class DefaultExceptionHandler {
    private static final Logger logger = Logger.newInstance(DefaultExceptionHandler.class);

    /**
     * 异常统一处理方法
     */
    public static BaseResult<Object> handleException(Throwable e) {
        BaseResult<Object> resultBean = new BaseResult<>();
        CodeException.ExceptionInfo code;
        try {
            code = handleRealException(e);
        } catch (Throwable throwable) {
            logger.printDebugStackTrace(e);
            code = ResultCodes.Code.COMMON_ERROR_UNKNOW;
        }
        resultBean.setState(code.getCode());
        resultBean.setLevel(code.getLevel());
        if (ApplicationEnvConfig.DEBUG) {
            if (e instanceof CodeException) {
                resultBean.setErrormsg(code.getMsg());
            } else {
                resultBean.setErrormsg(code.getMsg() + " : " + e.getMessage());
            }
        } else {
            resultBean.setErrormsg(code.getMsg());
        }
        logger.info("Controller response body : {}", resultBean.toString());
        return resultBean;
    }

    /**
     * 处理Exception
     */
    private static CodeException.ExceptionInfo handleRealException(Throwable e) {
        boolean printLog = true;
        CodeException.ExceptionInfo code = null;
        if (e instanceof NumberFormatException
                || e instanceof HttpMessageNotReadableException
                || e instanceof MethodArgumentNotValidException) {
            //Validate 框架验证参数错误
            code = ResultCodes.Code.COMMON_ERROR_PARAMS;
        } else if (e instanceof CodeException) {
            logger.printDebugStackTrace(e);
            printLog = false;
            // 业务验证错误
            code = ((CodeException) e).getCode();
        } else if (e instanceof SQLException
                || e instanceof MyBatisSystemException
                || e instanceof InvalidResultSetAccessException
                || e instanceof BadSqlGrammarException) {
            code = ResultCodes.Code.COMMON_ERROR_DATABASE;
        } else if (e instanceof OutOfMemoryError) {
            logger.error("OutOfMemoryError:", e);
            System.exit(0);
        } else {
            code = ResultCodes.Code.COMMON_ERROR_UNKNOW;
        }
        if (printLog) {
            logger.printStackTrace(e);
        }
        return code;
    }

    /**
     * 默认异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        return handleException(e);
    }
}
