package hello.externalread.config;

import hello.externalread.datasource.MyDataSource;
import hello.externalread.datasource.MyDataSourcePropertiesV1;
import hello.externalread.datasource.MyDataSourcePropertiesV2;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@EnableConfigurationProperties(MyDataSourcePropertiesV2.class)
public class MyDataSourceConfigV2 {

    private final MyDataSourcePropertiesV2 myDataSourcePropertiesV2;

    @Bean
    public MyDataSource myDataSource() {
        return new MyDataSource(
                myDataSourcePropertiesV2.getUrl(),
                myDataSourcePropertiesV2.getUsername(),
                myDataSourcePropertiesV2.getPassword(),
                myDataSourcePropertiesV2.getEtc().getMaxConnection(),
                myDataSourcePropertiesV2.getEtc().getTimeout(),
                myDataSourcePropertiesV2.getEtc().getOptions()
        );
    }

}
