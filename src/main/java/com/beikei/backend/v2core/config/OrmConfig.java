package com.beikei.backend.v2core.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Optional;

/**
 * orm配置，目前使用的是mybatis，并设置多数据源
 * 业务数据源和日志数据源
 * 业务数据源后期可能会再进行拆分，将旧数据迁移至备份数据源等
 * @author bk
 */
@Configuration
public class OrmConfig {

    protected static DataSource createDataSource(DataSourceProperties properties, Class<? extends DataSource> type) {
        return properties.initializeDataSourceBuilder().type(type).build();
    }

    @Bean("primaryProperties")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public V2DataSourceProperties properties() {
        return new V2DataSourceProperties();
    }

    @Bean(name = "primaryDatasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary.hikari")
    public HikariDataSource primaryDataSource(@Qualifier("primaryProperties") V2DataSourceProperties properties) {
        HikariDataSource dataSource = (HikariDataSource) OrmConfig.createDataSource(properties, HikariDataSource.class);
        String poolName = Optional.ofNullable(properties.getName()).orElse("primary_hikari_pool");
        dataSource.setPoolName(poolName);
        return dataSource;
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager primaryDatasourceTransactionManager(@Qualifier("primaryDatasource") HikariDataSource primaryDatasource) {
        return new DataSourceTransactionManager(primaryDatasource);
    }

    @Bean("primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDatasource") HikariDataSource primaryDatasource,
                                                      @Qualifier("primaryProperties") V2DataSourceProperties properties) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(primaryDatasource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource(properties.getMapperLocation()));
        factoryBean.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        factoryBean.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        return factoryBean.getObject();
    }

}
