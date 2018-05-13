package org.demon.code;


import org.demon.bean.BaseResult;
import org.demon.exception.CodeException;
import org.demon.util.I18nUtil;
import org.demon.util.StringUtil;

import java.util.Locale;

/**
 * Description: 返回状态码
 * Date: 2016/10/11 14:02
 *
 * @author Sean.xie
 */
public final class ResultCodes {
    public static enum Code implements CodeException.ExceptionInfo {
        /**
         * 成功
         */
        COMMON_SUCCESS(Common.SUCCESS, "code_success"),

        /**
         * 未登录
         */
        COMMON_ERROR_NOT_LOGINED(Common.ERROR_NOT_LOGINED, "code_error_not_logined"),
        /**
         * 登录已过期
         */
        COMMON_ERROR_LOGINED_TIMEOUT(Common.ERROR_NOT_LOGINED, "code_error_login_timeout"),
        /**
         * 数据异常
         */
        COMMON_ERROR_DATA_INVALID(Common.ERROR_DATA_INVALID, "code_error_data_invalid"),
        /**
         * 验证失败
         */
        COMMON_ERROR_AUTHORITY_FAILED(Common.ERROR_AUTHORITY_FAILED, "code_error_authority_failed"),
        /**
         * 操作不支持
         */
        COMMON_ERROR_NOT_SUPPORTED(Common.ERROR_NOT_SUPPORTED, "code_error_not_supported"),
        /**
         * 操作超时
         */
        COMMON_ERROR_TIMEOUT(Common.ERROR_TIMEOUT, "code_error_timeout"),
        /**
         * 参数错误
         */
        COMMON_ERROR_PARAMS(Common.ERROR_PARAMS, "code_error_params"),
        /**
         * 数据不存在
         */
        COMMON_ERROR_NOT_EXIST(Common.ERROR_NOT_EXIST, "code_error_not_exist"),
        /**
         * 数据已存在
         */
        COMMON_ERROR_EXIST(Common.ERROR_EXIST, "code_error_exist"),
        /**
         * 参数不是JSON格式
         */
        COMMON_ERROR_PARAMS_NOT_JSON(Common.ERROR_PARAMS_NOT_JSON, "code_error_params_not_json"),
        /**
         * 未知错误
         */
        COMMON_ERROR_UNKNOW(Common.ERROR_UNKNOW, "code_error_unkonw"),
        /**
         * 数据库错误
         */
        COMMON_ERROR_DATABASE(Common.ERROR_DATABASE, "code_error_database"),
        /**
         * 数据库操作失败
         */
        COMMON_ERROR_DATABASE_OPT_FAILED(Common.ERROR_DATABASE_OPT_FAILED, "code_error_database_opt_failed"),
        /**
         * Redis错误
         */
        COMMON_ERROR_REDIS(Common.ERROR_REDIS, "code_error_redis"),
        /**
         * 网络错误
         */
        COMMON_ERROR_NET(Common.ERROR_NET, "code_error_net"),
        /**
         * Kafka错误
         */
        COMMON_ERROR_KAFKA(Common.ERROR_KAFKA, "code_error_kafka"),
        /**
         * 签名错误
         */
        COMMON_ERROR_SIGN(Common.ERROR_SIGN, "code_error_sign"),
        /**
         * Header错误
         */
        COMMON_ERROR_HEADER(Common.ERROR_HEADER, "code_error_header"),
        /**
         * 同步方法错误
         */
        COMMON_ERROR_SYNC(Common.ERROR_SYNC, "code_error_sync"),
        /**
         * 同步方法超时错误
         */
        COMMON_ERROR_SYNC_TIMEOUT(Common.ERROR_SYNC_TIMEOUT, "code_error_sync_timeout"),

        /**
         * 业务异常
         */
        COMMON_ERROR_BIZ(Common.ERROR_BIZ, "code_error_biz"),
        /**
         * 业务取消操作
         */
        COMMON_ERROR_BIZ_CANCEL(Common.ERROR_BIZ_CANCEL, "code_error_biz_cancel"),

        /**
         * 融云错误
         */
        COMMON_ERROR_THIRD_PART_RONGCLOUD(Common.ERROR_THIRD_PART_RONGCLOUD, "code_error_rongcloud"),
        /**
         * 短信通道错误
         */
        COMMON_ERROR_THIRD_PART_SMS(Common.ERROR_THIRD_PART_SMS, "code_error_sms"),
        /**
         * 邮件通道错误
         */
        COMMON_ERROR_THIRD_PART_EMAIL(Common.ERROR_THIRD_PART_EMAIL, "code_error_email");
        /**
         * 状态码
         */
        private int code;
        private CodeException.Level level;
        /**
         * 消息
         */
        private String msg;

        Code(int code, String msg) {
            this.code = code;
            this.msg = msg;
            this.level = CodeException.Level.NONE;
        }

        public static String getString(String key, Object... args) {
            return I18nUtil.getLocalResource(Locale.getDefault(), "values/error_base_strings", key, args);
        }

        public String getMsg() {
            return getString(msg);
        }

        @Override
        public Integer getLevel() {
            return level.getValue();
        }

        @Override
        public Code setLevel(CodeException.Level level) {
            this.level = level;
            return this;
        }

        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            return String.format("code:%d,msg:%s", code, getMsg());
        }
    }

    /**
     * 自定义错误信息,枚举错误不能准确描述异常信息时,或异常信息带有业务解释时,使用此类
     */
    public static final class DataCode implements CodeException.ExceptionInfo {

        private CodeException.ExceptionInfo code;
        private CodeException.Level level;
        private String msg;

        public DataCode(CodeException.ExceptionInfo code, String msg) {
            this.code = code;
            if (code == null) {
                throw new IllegalArgumentException("code can not be null");
            }
            this.msg = msg;
            if (StringUtil.isEmpty(msg)) {
                throw new UnsupportedOperationException("not need to call this method");
            }
            this.level = CodeException.Level.NONE;
        }

        @Override
        public int getCode() {
            return code.getCode();
        }

        @Override
        public Integer getLevel() {
            return level.getValue();
        }

        @Override
        public DataCode setLevel(CodeException.Level level) {
            this.level = level;
            return this;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        @Override
        public String toString() {
            return String.format("code:%d,msg:%s", code.getCode(), getMsg());
        }
    }

    public static final class Common {
        // 成功 1000
        public static final int SUCCESS = BaseResult.CODE_SUCCESS;
        // 未知错误
        public static final int ERROR_UNKNOW = -1;
        // 未登录
        public static final int ERROR_NOT_LOGINED = 1;
        // 数据异常
        public static final int ERROR_DATA_INVALID = 2;
        // 校验失败
        public static final int ERROR_AUTHORITY_FAILED = 3;
        // 不支持此操作
        public static final int ERROR_NOT_SUPPORTED = 10;
        // 操作超时
        public static final int ERROR_TIMEOUT = 20;
        // 参数错误
        public static final int ERROR_PARAMS = 1100;
        // JSON 校验失败
        public static final int ERROR_PARAMS_NOT_JSON = 1101;
        // 查不到指定数据
        public static final int ERROR_NOT_EXIST = 1151;
        // 数据已存在,不可重复
        public static final int ERROR_EXIST = 1152;
        // 数据库错误
        public static final int ERROR_DATABASE = 1200;
        // 数据库操作失败
        public static final int ERROR_DATABASE_OPT_FAILED = 1201;
        // REDIS 异常
        public static final int ERROR_REDIS = 1300;
        // 网络异常
        public static final int ERROR_NET = 1400;
        // Kafka 连接
        public static final int ERROR_KAFKA = 1500;
        // 签名校验失败
        public static final int ERROR_SIGN = 1600;
        // Header 校验失败
        public static final int ERROR_HEADER = 1620;

        // 同步方法异常
        public static final int ERROR_SYNC = 1700;
        // 同步方法超时
        public static final int ERROR_SYNC_TIMEOUT = 1701;

        //---------------------- 业务错误使用20000 -30000  ------------------------
        // 业务异常 业务相关 20000+
        public static final int ERROR_BIZ = 20000;
        // 操作取消
        public static final int ERROR_BIZ_CANCEL = 20100;
        //------------- 第三方错误使用 30000 -40000 --------------------
        // 第三方返回失败
        public static final int ERROR_THIRD_PART = 31700;
        // 融云错误
        public static final int ERROR_THIRD_PART_RONGCLOUD = 31701;
        // 邮件通道错误
        public static final int ERROR_THIRD_PART_EMAIL = 31702;
        // 短信通道错误
        public static final int ERROR_THIRD_PART_SMS = 31703;
    }
}
