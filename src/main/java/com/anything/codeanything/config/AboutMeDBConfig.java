package com.anything.codeanything.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages  = "com.anything.codeanything.mapper.aboutme", sqlSessionFactoryRef = "aboutmeSqlSessionFactory") // Specify the package for AboutMe mappers
public class AboutMeDBConfig {

    @Bean(name = "aboutmeDataSourceProperties")
    @ConfigurationProperties("spring.datasource.aboutme") // Properties prefix for the second database
    public DataSourceProperties aboutmeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "aboutmeDataSource")
    public DataSource aboutmeDataSource() {
        return aboutmeDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "aboutmeSqlSessionFactory")
    public SqlSessionFactory aboutmeSqlSessionFactory(@Qualifier("aboutmeDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        // Specify the location of MyBatis mapper XML files for the aboutme database
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mapper/aboutme/*.xml");
        sessionFactoryBean.setMapperLocations(resources);

        return sessionFactoryBean.getObject();
    }
}
