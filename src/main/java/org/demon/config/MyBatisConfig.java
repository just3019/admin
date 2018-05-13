package org.demon.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.demon.datasource.MultipleDataSource;
import org.demon.util.JSONUtil;
import org.demon.util.Logger;
import org.demon.util.StringUtil;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * springboot集成mybatis的基本入口 1）创建数据源
 * 2）创建SqlSessionFactory 3）配置事务管理器，除非需要使用事务，否则不用配置
 */

/**
 * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
 * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
 */
@Configuration
@ConditionalOnClass(BasicDataSource.class)
@EnableConfigurationProperties(DataSourceProperty.class)
public class MyBatisConfig implements EnvironmentAware {
    private Environment env;
    private Logger logger = Logger.newInstance(MyBatisConfig.class);

    /**
     * 创建主数据源
     *
     * @param dataSourceProperty
     */
    public DataSource defaultDataSource(DataSourceProperty dataSourceProperty) throws Exception {
        logger.info(JSONUtil.obj2PrettyJson(dataSourceProperty));
        // 读取主数据源
        Properties props = new Properties();
        props.put("driverClassName", dataSourceProperty.getDriverClassName());
        props.put("url", dataSourceProperty.getUrl());
        props.put("username", dataSourceProperty.getUsername());
        props.put("password", dataSourceProperty.getPassword());
        setDBPoolArgs(props, dataSourceProperty.getDbcp2());
        return BasicDataSourceFactory.createDataSource(props);
    }

    /**
     * 其他数据源
     *
     * @param dataSourceProperty
     */
    public Map<String, DataSource> otherDataSources(DataSourceProperty dataSourceProperty) throws Exception {
        // 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        Map<String, DataSourceProperty> multiDataSource = dataSourceProperty.getMultiDatasource();
        if (multiDataSource == null || multiDataSource.size() == 0) {
            return null;
        }
        Properties props = null;
        for (Map.Entry<String, DataSourceProperty> entry : multiDataSource.entrySet()) {// 多个数据源
            props = new Properties();
            DataSourceProperty property = entry.getValue();
            if (StringUtil.isEmpty(property.getDriverClassName())) {
                property.setDriverClassName(dataSourceProperty.getDriverClassName());
            }
            props.put("driverClassName", property.getDriverClassName());
            props.put("url", property.getUrl());
            props.put("username", property.getUsername());
            props.put("password", property.getPassword());
            setDBPoolArgs(props, dataSourceProperty.getDbcp2());
            dataSourceMap.put(entry.getKey(), BasicDataSourceFactory.createDataSource(props));
        }
        return dataSourceMap;
    }

    private void setDBPoolArgs(Properties properties, DataSourceProperty.DBCP2 dbcp2) {
        properties.put("initialSize", dbcp2.getInitialSize());
        properties.put("minIdle", dbcp2.getMinIdle());
        properties.put("maxIdle", dbcp2.getMaxIdle());
        properties.put("maxTotal", dbcp2.getMaxTotal());
        properties.put("maxWaitMillis", dbcp2.getMaxWaitMillis());
        properties.put("testOnReturn", dbcp2.isTestOnReturn());
        properties.put("testOnBorrow", dbcp2.isTestOnBorrow());
        properties.put("testOnCreate", dbcp2.isTestOnCreate());
        properties.put("validationQuery", dbcp2.getValidationQuery());
        if (dbcp2.getValidationQueryTimeout() != null) {
            properties.put("validationQueryTimeout", dbcp2.getValidationQueryTimeout());
        }
    }

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean
    @Autowired
    public DataSource dataSource(DataSourceProperty dataSourceProperty) {
        DataSource defaultDataSource = null;
        try {
            defaultDataSource = defaultDataSource(dataSourceProperty);
        } catch (Exception e) {
            logger.printStackTrace(e);
        }
        Map<String, DataSource> otherDataSources = null;
        try {
            otherDataSources = otherDataSources(dataSourceProperty);
        } catch (Exception e) {
            logger.printStackTrace(e);
        }
        if (defaultDataSource == null) {
            throw new IllegalStateException("Default DataSource can not null");
        }

        if (otherDataSources == null) {
            return defaultDataSource;
        }
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("defaultDataSource", defaultDataSource);
        for (Map.Entry<String, DataSource> entry : otherDataSources.entrySet()) {
            targetDataSources.put(entry.getKey(), entry.getValue());
        }

        MultipleDataSource dataSource = new MultipleDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(defaultDataSource);// 默认的datasource设置为myTestDbDataSource

        return dataSource;
    }

    /**
     * 配置事务管理器
     */
    @Bean
    @Order
    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 加载顺序太靠前,不能使用Autowired 传参数
     *
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(env.getProperty("mybatis.mapper.basePackage"));
        return mapperScannerConfigurer;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }
}
