package com.kacably.xcj.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = MasterDbConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDbConfig {

    public static final String PACKAGE = "com.kacably.xcj.mapper.xcj";

    @Value("${spring.datasource.url1}")
    private String dbUrl;

    @Value("${spring.datasource.username1}")
    private String username;

    @Value("${spring.datasource.password1}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    //取配置文件中的这个属性
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Bean(name="masterDataSource")
    @Primary
    public DataSource masterDataSource(){
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        return datasource;
    }

    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager(){
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource, MybatisProperties mybatisProperties)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(mapperLocations));
        //mybatis 数据库字段与实体类属性驼峰映射配置
        sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(mybatisProperties.getConfiguration());
        return sessionFactory.getObject();
    }

}
