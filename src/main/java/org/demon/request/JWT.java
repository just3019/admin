package org.demon.request;


import org.demon.util.Base64;
import org.demon.util.DateUtil;
import org.demon.util.JSONUtil;
import org.demon.util.Logger;
import org.demon.util.MD5;
import org.demon.util.SHA;
import org.joda.time.DateTime;

import static org.demon.request.JWT.Header.ALGORITHM_HMACSHA256;

/**
 * JWT
 */
public class JWT {
    public static final Header DEFAULT_HEADER;
    public static final Payload DEFAULT_PAYLOAD;
    public static final Clock DEFAULT_CLOCK = new Clock() {
        @Override
        public long currentTime() {
            return System.currentTimeMillis();
        }
    };
    private static final Logger logger = Logger.newInstance(JWT.class);
    private static Clock clock;

    static {
        DEFAULT_HEADER = new Header();
        DEFAULT_HEADER.alg = ALGORITHM_HMACSHA256;

        DEFAULT_PAYLOAD = new Payload();
        DEFAULT_PAYLOAD.setExp(7 * 24 * 3600 * 1000L);
    }

    @JSONUtil.ExcludeField
    private final String secretKey;
    public Header header;
    public Payload payload;
    public String signature;

    public JWT(String secretKey) {
        this.secretKey = secretKey;
        header = DEFAULT_HEADER;
        payload = DEFAULT_PAYLOAD;
    }

    /**
     * 创建 JWT
     */
    public static JWT newJWT(String secretKey) {
        return new JWT(secretKey);
    }

    private byte[] getKey() {
        return SHA.getSHA256Hex(secretKey.trim()).getBytes();
    }

    public JWT getJWT(String jwtValue) {
        return getJWT(jwtValue, null);
    }

    public <T> JWT getJWT(String jwtValue, Class<T> attrClass) {
        try {
            String[] parts = jwtValue.split("\\.");
            String sign = MD5.md5(SHA.getHMACSHA256(parts[0] + parts[1], getKey()));
            if (sign.equals(parts[2])) {
                JWT result = new JWT(secretKey);
                result.header = JSONUtil.json2Obj(Base64.decode2Sting(parts[0]), Header.class);
                result.payload = JSONUtil.json2Obj(Base64.decode2Sting(parts[1]), attrClass == null ? Payload.class :
                        attrClass);
                result.signature = parts[2];
                return result;
            }
        } catch (Exception e) {
            logger.printStackTrace(e);
        }
        return null;
    }

    /**
     * 创建 JWT
     */
    public JWT newJWT() {
        return new JWT(secretKey);
    }

    /**
     * 获取时钟
     */
    public Clock getClock() {
        if (JWT.clock == null) {
            return DEFAULT_CLOCK;
        }
        return JWT.clock;
    }

    /**
     * 设置时钟
     */
    public JWT setClock(Clock clock) {
        JWT.clock = clock;
        return this;
    }

    @Override
    public String toString() {
        String header = Base64.encode(JSONUtil.obj2Json(this.header));
        String payload = Base64.encode(JSONUtil.obj2Json(this.payload));
        if (ALGORITHM_HMACSHA256.equals(this.header.alg)) {
            this.signature = MD5.md5(SHA.getHMACSHA256(header + payload, getKey()));
        } else {
            throw new IllegalArgumentException("algorithm not support");
        }
        return header + "." + payload + "." + signature;
    }

    /**
     * 时钟
     */
    public interface Clock {
        long currentTime();
    }

    public static class Header {
        public static final int TYPE_REFRESH_EXP = 1;
        public static final String ALGORITHM_HMACSHA256 = "HA";
        /**
         * 类型
         */
        public Integer typ = 0;
        /**
         * 算法
         */
        public String alg;

        /**
         * 是否刷新jwt
         */
        public boolean isRefresh() {
            return TYPE_REFRESH_EXP == typ;
        }
    }

    public static class Payload<T> {
        /**
         * jwt签发者
         */
        public String iss;
        /**
         * jwt所面向的用户
         */
        public String sub;
        /**
         * jwt来源
         */
        public String src;
        /**
         * 接收jwt的一方
         */
        public String aud;
        /**
         * jwt的过期时间，这个过期时间必须要大于签发时间
         */
        public Long exp;
        /**
         * jwt的签发时间
         */
        public Long nbf;
        /**
         * 定义在什么时间之前，该jwt都是不可用的.
         */
        public Long iat;
        /**
         * jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
         */
        public String jti;
        /**
         * 用户ID
         */
        public String uid;
        /**
         * 自定义属性
         */
        public T attr;

        public Payload() {
        }

        /**
         * 有效期
         *
         * @param period
         */
        public Payload(Long period) {
            setExp(period);
        }

        /**
         * 设置有效期
         *
         * @param period
         */
        public void setExp(Long period) {
            if (clock != null) {
                nbf = clock.currentTime();
            } else {
                nbf = DateUtil.now().getMillis();
            }
            DateTime dateTime = new DateTime(nbf.longValue()).plus(period);
            int hourOfDay = dateTime.hourOfDay().get();
            int delta = 26 - hourOfDay;
            exp = dateTime.plusHours(delta).getMillis();
        }
    }

}