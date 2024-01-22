package com.anything.codeanything.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.anything.codeanything.mapper") // Specify the package where your mapper interfaces are located
public class MyBatisConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        // Specify the location of MyBatis mapper XML files
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mapper/*.xml");
        sessionFactoryBean.setMapperLocations(resources);

        // Other MyBatis configurations if needed...

        return sessionFactoryBean.getObject();
    }

//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    @Bean
//    public SqlSessionManager sqlSessionManager(SqlSessionFactory sqlSessionFactory) throws Exception {
//        return SqlSessionManager.newInstance(sqlSessionFactory);
//    }
}
