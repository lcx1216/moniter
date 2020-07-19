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
@MapperScan(basePackages ="com.gis.monitor.mapper.eqinfo", sqlSessionTemplateRef  = "eqinfoSqlSessionTemplate")
class EqInfoConfig {
    @Bean(name = "eqinfoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.eqinfo")
    public DataSource eqinfoDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "eqinfoSqlSessionFactory")
    public SqlSessionFactory eqinfoSqlSessionFactory(@Qualifier("eqinfoDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/eqinfo/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "eqinfoTransactionManager")
    public DataSourceTransactionManager eqinfoTransactionManager(@Qualifier("eqinfoDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "eqinfoSqlSessionTemplate")
    public SqlSessionTemplate eqinfoSqlSessionTemplate(@Qualifier("eqinfoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}