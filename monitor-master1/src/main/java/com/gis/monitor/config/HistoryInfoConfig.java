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
    配置数据库 JC_Historyinfo
 */

@Configuration
@MapperScan(basePackages ="com.gis.monitor.mapper.historyinfo", sqlSessionTemplateRef  = "historyinfoSqlSessionTemplate")
class HistoryInfoConfig {
    @Bean(name = "historyinfoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.historyinfo")
    public DataSource historyinfoDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "historyinfoSqlSessionFactory")
    public SqlSessionFactory historyinfoSqlSessionFactory(@Qualifier("historyinfoDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/historyinfo/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "historyinfoTransactionManager")
    public DataSourceTransactionManager historyinfoTransactionManager(@Qualifier("historyinfoDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "historyinfoSqlSessionTemplate")
    public SqlSessionTemplate historyinfoSqlSessionTemplate(@Qualifier("historyinfoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}