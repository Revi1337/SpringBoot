package hello.externalread.config;

import hello.externalread.datasource.MyDataSource;
import hello.externalread.datasource.MyDataSourcePropertiesV1;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@EnableConfigurationProperties(MyDataSourcePropertiesV1.class)
public class MyDataSourceConfigV1 {

    private final MyDataSourcePropertiesV1 myDataSourcePropertiesV1;

    @Bean
    public MyDataSource myDataSource() {
        return new MyDataSource(
                myDataSourcePropertiesV1.getUrl(),
                myDataSourcePropertiesV1.getUsername(),
                myDataSourcePropertiesV1.getPassword(),
                myDataSourcePropertiesV1.getEtc().getMaxConnection(),
                myDataSourcePropertiesV1.getEtc().getTimeout(),
                myDataSourcePropertiesV1.getEtc().getOptions()
        );
    }
}
