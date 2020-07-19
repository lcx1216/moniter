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
    配置数据库 JC_MANAGEGRADE
 */
@Configuration
@MapperScan(basePackages ="com.gis.monitor.mapper.managegrade", sqlSessionTemplateRef  = "managegradeSqlSessionTemplate")
public class ManageGradeConfig {
    // 管理得分库
    @Bean(name = "managegradeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.managegrade")
    public DataSource managegradeDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "managegradeSqlSessionFactory")
    public SqlSessionFactory managegradeSqlSessionFactory(@Qualifier("managegradeDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/managegrade/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "managegradeTransactionManager")
    public DataSourceTransactionManager managegradeTransactionManager(@Qualifier("managegradeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "managegradeSqlSessionTemplate")
    public SqlSessionTemplate managegradeSqlSessionTemplate(@Qualifier("managegradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
