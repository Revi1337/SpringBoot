package hello.externalread;

import hello.externalread.config.MyDataSourceConfigV1;
import hello.externalread.config.MyDataSourceConfigV2;
import hello.externalread.config.MyDataSourceConfigV3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;


@Import(MyDataSourceConfigV3.class)
//@Import(MyDataSourceConfigV2.class)
//@Import(MyDataSourceConfigV1.class) @ConfigurationPropertiesScan
//@Import(MyDatasourceValueConfig.class)
//@Import(MyDatasourceEnvConfig.class)
@SpringBootApplication(scanBasePackages = "hello.externalread.datasource")
public class ExternalReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalReadApplication.class, args);
    }

}
