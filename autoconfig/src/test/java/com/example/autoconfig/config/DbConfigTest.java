package com.example.autoconfig.config;

import com.example.autoconfig.AutoconfigApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatPath;

@SpringBootTest @Slf4j
class DbConfigTest {

    private final DataSource dataSource;

    private final TransactionManager transactionManager;

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public DbConfigTest(DataSource dataSource, TransactionManager transactionManager, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.transactionManager = transactionManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    @DisplayName(value = """
        DbConfig 에 @Configuration 어노테이션을 달지않아도 SpringBoot 의 AutoConfiguration 기능이 자동으로 해당 타입의 Bean 들을 등록해줌.
        --> DataSource, TransactionManager, JdbcTemplate 빈은 SpringBoot 에서 자동으로 등록해주는 기본 Bean 들이기 때문임.
    """)
    public void checkBean() {
        log.info("dataSource = {}", dataSource);
        log.info("transactionManager = {}", transactionManager);
        log.info("jdbcTemplate = {}", jdbcTemplate);

        assertThat(dataSource).isNotNull();
        assertThat(transactionManager).isNotNull();
        assertThat(transactionManager).isNotNull();
    }
    
}