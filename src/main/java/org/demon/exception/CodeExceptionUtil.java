package org.demon.exception;

import org.demon.code.ResultCodes;

/**
 * Description:
 * Created by Sean.xie on 2017/4/21.
 */
public class CodeExceptionUtil {

    /**
     * 抛异常
     */
    @Deprecated
    public static void throwExeception(CodeException.ExceptionInfo code, String... msg) {
        throw new CodeException(code, msg);
    }

    /**
     * 抛异常
     */
    @Deprecated
    public static void throwExeception(int code, String msg) {
        throw new CodeException(new CodeStatus(code, msg));
    }

    /**
     * 抛异常
     */
    public static void throwException(CodeException.ExceptionInfo code, String... msg) {
        throw new CodeException(code, msg);
    }

    /**
     * 抛异常
     */
    public static void throwException(int code, String msg) {
        throw new CodeException(new CodeStatus(code, msg));
    }

    /**
     * 参数错误
     *
     */
    public static void throwParamException(String... msg) {
        throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, msg);
    }

    /**
     * 参数错误
     *
     */
    public static CodeException newParamException(String... msg) {
        return new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, msg);
    }

    /**
     * 数据不存在
     *
     */
    public static void throwNotExistException(String... msg) {
        throw new CodeException(ResultCodes.Code.COMMON_ERROR_NOT_EXIST, msg);
    }

    /**
     * 数据不存在
     *
     */
    public static CodeException newNotExistException(String... msg) {
        return new CodeException(ResultCodes.Code.COMMON_ERROR_NOT_EXIST, msg);
    }

    /**
     * 其他服务异常
     *
     */
    public static void throwThirdBizException(String... msg) {
        throw new CodeException(ResultCodes.Code.COMMON_ERROR_BIZ, msg);
    }

    /**
     * 其他服务异常
     *
     */
    public static CodeException newThirdBizException(String... msg) {
        return new CodeException(ResultCodes.Code.COMMON_ERROR_BIZ, msg);
    }

    /**
     * Kafka异常
     *
     */
    public static void throwKafkaException(String... msg) {
        throw new CodeException(ResultCodes.Code.COMMON_ERROR_KAFKA, msg);
    }

    /**
     * Kafka异常
     *
     */
    public static CodeException newKafkaException(String... msg) {
        return new CodeException(ResultCodes.Code.COMMON_ERROR_KAFKA, msg);
    }

    /**
     * 操作不支持
     *
     */
    public static void throwNotSupportException(String... msg) {
        throw new CodeException(ResultCodes.Code.COMMON_ERROR_NOT_SUPPORTED, msg);
    }

    /**
     * 操作不支持
     *
     */
    public static CodeException newNotSupportException(String... msg) {
        return new CodeException(ResultCodes.Code.COMMON_ERROR_NOT_SUPPORTED, msg);
    }

    /**
     * CodeStatus
     */
    public static class CodeStatus implements CodeException.ExceptionInfo {
        private final String msg;
        private final int code;
        private CodeException.Level level;

        public CodeStatus(int code, String msg) {
            this.code = code;
            this.msg = msg;
            this.level = CodeException.Level.NONE;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public Integer getLevel() {
            return level.getValue();
        }

        @Override
        public CodeStatus setLevel(CodeException.Level level) {
            this.level = level;
            return this;
        }

        @Override
        public String getMsg() {
            return msg;
        }


        @Override
        public String toString() {
            return String.format("code:%d,msg:%s", code, getMsg());
        }
    }
}
