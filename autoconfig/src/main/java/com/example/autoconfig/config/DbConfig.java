package com.example.autoconfig.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Slf4j
public class DbConfig {

    @Bean
    public DataSource dataSource() {
        log.info("DataSource Bean Registered");
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName("org.h2.Driver");
        hikariDataSource.setJdbcUrl("jdbc:h2:mem:test");
        hikariDataSource.setUsername("sa");
        hikariDataSource.setPassword("");
        return hikariDataSource;
    }

    @Bean
    public TransactionManager transactionManager() {
        log.info("TransactionManager Bean Registered");
        return new JdbcTransactionManager(dataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        log.info("JdbcTemplate Bean Registered");
        return new JdbcTemplate(dataSource());
    }

}
