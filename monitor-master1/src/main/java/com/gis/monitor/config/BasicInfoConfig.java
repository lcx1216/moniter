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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/*
    数据库配置文件
    配置数据库 JC_BASICINFO
 */
@Configuration
@MapperScan(basePackages ="com.gis.monitor.mapper.basicinfo", sqlSessionTemplateRef  = "basicinfoSqlSessionTemplate")
class BasicInfoConfig {
    // 主数据源配置
    // 基础信息库
    @Primary
    @Bean(name = "basicinfoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.basicinfo")
    public DataSource basicinfoDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "basicinfoSqlSessionFactory")
    @Primary
    public SqlSessionFactory basicinfoSqlSessionFactory(@Qualifier("basicinfoDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/basicinfo/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "basicinfoTransactionManager")
    @Primary
    public DataSourceTransactionManager basicinfoTransactionManager(@Qualifier("basicinfoDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "basicinfoSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate basicinfoSqlSessionTemplate(@Qualifier("basicinfoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
