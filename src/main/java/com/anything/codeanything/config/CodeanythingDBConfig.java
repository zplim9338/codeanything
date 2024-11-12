package com.anything.codeanything.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.anything.codeanything.mapper.codeanything", sqlSessionFactoryRef = "codeanythingSqlSessionFactory") // Specify the package for Codeanything mappers
public class CodeanythingDBConfig {

    @Primary
    @Bean(name = "codeanythingDataSourceProperties")
    @ConfigurationProperties("spring.datasource") // Properties prefix for the first database
    public DataSourceProperties codeanythingDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "codeanythingDataSource")
    public DataSource codeanythingDataSource() {
        return codeanythingDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "codeanythingSqlSessionFactory")
    public SqlSessionFactory codeanythingSqlSessionFactory(@Qualifier("codeanythingDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        // Specify the location of MyBatis mapper XML files for the codeanything database
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mapper/codeanything/*.xml");
        sessionFactoryBean.setMapperLocations(resources);

        return sessionFactoryBean.getObject();
    }
}
