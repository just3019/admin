package org.demon.config;

import org.apache.commons.pool2.impl.BaseObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Description: 数据源配置项 spring.datasource.* 对应树结构
 * Created by Sean.xie on 2017/2/4.
 */
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperty {

    /**
     * 数据库连接URL
     */
    private String url;
    /**
     * 数据库登录用户
     */
    private String username;
    /**
     * 数据库登录密码
     */
    private String password;
    /**
     * JDBC驱动
     */
    private String driverClassName;

    /**
     * DBCP2配置项
     */
//    @NotNull
    private DBCP2 dbcp2;

    /**
     * 多数据源配置项
     */
    private Map<String, DataSourceProperty> multiDatasource;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public DBCP2 getDbcp2() {
        return dbcp2;
    }

    public void setDbcp2(DBCP2 dbcp2) {
        this.dbcp2 = dbcp2;
    }

    public Map<String, DataSourceProperty> getMultiDatasource() {
        return multiDatasource;
    }

    public void setMultiDatasource(Map<String, DataSourceProperty> multiDatasource) {
        this.multiDatasource = multiDatasource;
    }

    /**
     * DBCP2配置项
     */
    public static class DBCP2 {
        private int maxTotal = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;
        private int maxIdle = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;
        private int minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;
        private int initialSize = 0;
        private long maxWaitMillis = BaseObjectPoolConfig.DEFAULT_MAX_WAIT_MILLIS;
        private boolean poolPreparedStatements = false;
        private int maxOpenPreparedStatements = GenericKeyedObjectPoolConfig.DEFAULT_MAX_TOTAL;
        private boolean testOnCreate = false;
        private boolean testOnBorrow = true;
        private boolean testOnReturn = false;
        private long timeBetweenEvictionRunsMillis = BaseObjectPoolConfig.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
        private int numTestsPerEvictionRun = BaseObjectPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;
        private long minEvictableIdleTimeMillis = BaseObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
        private volatile String validationQuery = null;
        private volatile Integer validationQueryTimeout = null;

        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getInitialSize() {
            return initialSize;
        }

        public void setInitialSize(int initialSize) {
            this.initialSize = initialSize;
        }

        public long getMaxWaitMillis() {
            return maxWaitMillis;
        }

        public void setMaxWaitMillis(long maxWaitMillis) {
            this.maxWaitMillis = maxWaitMillis;
        }

        public boolean isPoolPreparedStatements() {
            return poolPreparedStatements;
        }

        public void setPoolPreparedStatements(boolean poolPreparedStatements) {
            this.poolPreparedStatements = poolPreparedStatements;
        }

        public int getMaxOpenPreparedStatements() {
            return maxOpenPreparedStatements;
        }

        public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
            this.maxOpenPreparedStatements = maxOpenPreparedStatements;
        }

        public boolean isTestOnCreate() {
            return testOnCreate;
        }

        public void setTestOnCreate(boolean testOnCreate) {
            this.testOnCreate = testOnCreate;
        }

        public boolean isTestOnBorrow() {
            return testOnBorrow;
        }

        public void setTestOnBorrow(boolean testOnBorrow) {
            this.testOnBorrow = testOnBorrow;
        }

        public boolean isTestOnReturn() {
            return testOnReturn;
        }

        public void setTestOnReturn(boolean testOnReturn) {
            this.testOnReturn = testOnReturn;
        }

        public long getTimeBetweenEvictionRunsMillis() {
            return timeBetweenEvictionRunsMillis;
        }

        public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
            this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        }

        public int getNumTestsPerEvictionRun() {
            return numTestsPerEvictionRun;
        }

        public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
            this.numTestsPerEvictionRun = numTestsPerEvictionRun;
        }

        public long getMinEvictableIdleTimeMillis() {
            return minEvictableIdleTimeMillis;
        }

        public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
            this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        }

        public String getValidationQuery() {
            return this.validationQuery;
        }

        public void setValidationQuery(String validationQuery) {
            if (validationQuery != null && validationQuery.trim().length() > 0) {
                this.validationQuery = validationQuery;
            } else {
                this.validationQuery = null;
            }
        }

        public Integer getValidationQueryTimeout() {
            return validationQueryTimeout;
        }

        public void setValidationQueryTimeout(Integer timeout) {
            this.validationQueryTimeout = timeout;
        }
    }

}
