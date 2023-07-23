package hello.externalread.config;

import hello.externalread.datasource.MyDataSource;
import hello.externalread.datasource.MyDataSourcePropertiesV2;
import hello.externalread.datasource.MyDataSourcePropertiesV3;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@EnableConfigurationProperties(MyDataSourcePropertiesV3.class)
public class MyDataSourceConfigV3 {

    private final MyDataSourcePropertiesV3 myDataSourcePropertiesV3;

    @Bean
    public MyDataSource myDataSource() {
        return new MyDataSource(
                myDataSourcePropertiesV3.getUrl(),
                myDataSourcePropertiesV3.getUsername(),
                myDataSourcePropertiesV3.getPassword(),
                myDataSourcePropertiesV3.getEtc().getMaxConnection(),
                myDataSourcePropertiesV3.getEtc().getTimeout(),
                myDataSourcePropertiesV3.getEtc().getOptions()
        );
    }

}
