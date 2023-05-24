package com.example.autoconfig.config;

import com.example.autoconfig.AutoconfigApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
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

    @Test
    @DisplayName(value = "Spring Container 에 등록된 모든 Bean 을 출력")
    public void checkingBean() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AutoconfigApplication.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
    }

    @Test
    @DisplayName(value = "DbConfig 에 @Configuration 어노테이션을 달지않아도 Spring Boot 의 AutoConfiguration 기능에 의해 기본적인 Bean 들은 자동 등록된다.")
    public void checkingSpecifiedBean() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AutoconfigApplication.class);
        DataSource dataSourceBean = applicationContext.getBean(DataSource.class);
        TransactionManager transactionManagerBean = applicationContext.getBean(TransactionManager.class);
        JdbcTemplate jdbcTemplateBean = applicationContext.getBean(JdbcTemplate.class);

        System.out.println("jdbcTemplateBea    n = " + jdbcTemplateBean);
        System.out.println("dataSourceBean = " + dataSourceBean);
        System.out.println("transactionManagerBean = " + transactionManagerBean);
    }

//    @AutoConfiguration(after = DataSourceAutoConfiguration.class) : 자동 구성을 사용하려면 이 애노테이션을 등록해야 한다.
//        --> 자동 구성도 내부에 @Configuration 이 있어서 빈을 등록하는 자바 설정 파일로 사용할 수 있다.
//        --> after = DataSourceAutoConfiguration.class : 자동 구성이 실행되는 순서를 지정할 수 있다.
//            JdbcTemplate 은 DataSource 가 필요하기 때문에 DataSource 를 자동으로 등록해주는 DataSourceAutoConfiguration 다음에 실행하도록 설정되어 있다.
//    @ConditionalOnClass({ DataSource.class, JdbcTemplate.class })
//        --> IF 문과 유사한 기능을 제공한다. 이런 클래스가 있는 경우에만 설정이 동작한다. 만약 없으면 여기 있는 설정들이 모두 무효화 되고, 빈도 등록되지 않는다.
//            @ConditionalXxx 시리즈가 있다. AutoConfiguration 의 핵심이다.
//        --> JdbcTemplate 은 DataSource , JdbcTemplate 라는 클래스가 있어야 동작할 수 있다.
//    @Import : 스프링에서 자바 설정을 추가할 때 사용한다.

//    @Configuration : 자바 설정 파일로 사용된다.
//    @ConditionalOnMissingBean(JdbcOperations.class)
//      --> JdbcOperations 빈이 없을 때 동작한다. JdbcTemplate 의 부모 인터페이스가 바로 JdbcOperations 이다.
//      --> 쉽게 이야기해서 JdbcTemplate 이 빈으로 등록되어 있지 않은 경우에만 동작한다.
//      --> 만약 이런 기능이 없으면 내가 등록한 JdbcTemplate 과 자동 구성이 등록하는 JdbcTemplate 이 중복 등록되는 문제가 발생할 수 있다.
//      --> 보통 개발자가 직접 빈을 등록하면 개발자가 등록한 빈을 사용하고, 자동 구성은 동작하지 않는다.
//      --> JdbcTemplate 이 몇가지 설정을 거쳐서 빈으로 등록되는 것을 확인할 수 있다

//    https://docs.spring.io/spring-boot/docs/current/reference/html/auto-configuration-classes.html

}