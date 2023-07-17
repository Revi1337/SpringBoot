package hello.externalread;

import hello.externalread.config.MyDatasourceEnvConfig;
import hello.externalread.config.MyDatasourceValueConfig;
import hello.externalread.datasource.MyDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(MyDatasourceValueConfig.class)
//@Import(MyDatasourceEnvConfig.class)
@SpringBootApplication(scanBasePackages = "hello.externalread.datasource")
public class ExternalReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalReadApplication.class, args);
    }

}
