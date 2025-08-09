package com.hareshnayak.messaging_app.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DBConfig {

    @Value("${spring.datasource.url:jdbc:h2:mem:testdb}")
    private String dbUrl;
    @Value("${spring.datasource.username:sa}")
    private String dbUsername;
    @Value("${spring.datasource.password:}")
    private String dbPassword;
    @Value("${spring.datasource.driver-class-name:org.h2.Driver}")
    private String dbDriverClassName;
    @Value("${spring.datasource.hikari.maximum-pool-size:10}")
    private int dbMaxPoolSize;
    @Value("${spring.datasource.hikari.minimum-idle:2}")
    private int dbMinIdle;
    @Value("${spring.datasource.hikari.connection-timeout:30000}")
    private long dbConnectionTimeout;
    @Value("${spring.datasource.hikari.idle-timeout:600000}")
    private long dbIdleTimeout;
    @Value("${spring.datasource.hikari.max-lifetime:1800000}")
    private long dbMaxLifetime;
    @Value("${spring.datasource.hikari.pool-name:HikariCP}")
    private String dbPoolName;


    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName(dbDriverClassName);
        dataSource.setMaximumPoolSize(dbMaxPoolSize);
        dataSource.setMinimumIdle(dbMinIdle);
        dataSource.setConnectionTimeout(dbConnectionTimeout);
        dataSource.setIdleTimeout(dbIdleTimeout);
        dataSource.setMaxLifetime(dbMaxLifetime);
        dataSource.setPoolName(dbPoolName);
        return dataSource;
    }
}
