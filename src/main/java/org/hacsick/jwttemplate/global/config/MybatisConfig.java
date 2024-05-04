package org.hacsick.jwttemplate.global.config;

import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hacsick.jwttemplate.global.common.ApplicationConstant;
import org.hacsick.jwttemplate.global.intercepter.AnnotationTimeAuditInterceptor;
import org.hacsick.jwttemplate.global.intercepter.TimeAuditInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@MapperScan(
        basePackages = {ApplicationConstant.MYBATIS_BASE_PACKAGE},
        sqlSessionFactoryRef = "primarySqlSessionFactory"
)
public class MybatisConfig {


    @Bean
    @Primary
    @ConfigurationProperties(prefix = ApplicationConstant.SPRING_DATASOURCE_PATH)
    public DataSource primaryDataSource() {
        DataSource build = DataSourceBuilder.create().build();
        return build;
    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(
            @Qualifier("primaryDataSource") final DataSource primaryDataSource,
            final ApplicationContext applicationContext) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(primaryDataSource);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{
//                new TimeAuditInterceptor()
                new AnnotationTimeAuditInterceptor()
        });
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "primarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate primarySqlSessionTemplate(
            @Qualifier("primarySqlSessionFactory") SqlSessionFactory primarySqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(primarySqlSessionFactory);
    }
}
