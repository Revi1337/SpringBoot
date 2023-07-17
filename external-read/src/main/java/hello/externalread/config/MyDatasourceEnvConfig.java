package hello.externalread.config;

import hello.externalread.datasource.MyDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.time.Duration;
import java.util.List;

@Slf4j @Configuration @RequiredArgsConstructor
public class MyDatasourceEnvConfig {

    private final Environment environment;

    @Bean
    public MyDataSource myDataSource() {
        String url = environment.getProperty("my.datasource.url");
        String username = environment.getProperty("my.datasource.username");
        String password = environment.getProperty("my.datasource.password");
        int maxConnection = environment.getProperty("my.datasource.etc.max-connection", Integer.class);
        Duration timeout = environment.getProperty("my.datasource.etc.timeout", Duration.class);
        List<String> options = environment.getProperty("my.datasource.etc.options", List.class);
        return new MyDataSource(url, username, password, maxConnection, timeout, options);
    }

}
