package com.gis.monitor.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/*
    数据库配置文件
    配置数据库 QZDATA
 */

@Configuration
@MapperScan(basePackages ="com.gis.monitor.mapper.qzdata", sqlSessionTemplateRef  = "qzdataSqlSessionTemplate")
class QzdataConfig {
    @Bean(name = "qzdataDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.qzdata")
    public DataSource qzdataDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "qzdataSqlSessionFactory")
    public SqlSessionFactory qzdataSqlSessionFactory(@Qualifier("qzdataDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/qzdata/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "qzdataTransactionManager")
    public DataSourceTransactionManager qzdataTransactionManager(@Qualifier("qzdataDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "qzdataSqlSessionTemplate")
    public SqlSessionTemplate qzdataSqlSessionTemplate(@Qualifier("qzdataSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}