package hello.externalread.datasource;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.List;


@Validated @Data
@ConfigurationProperties("my.datasource")
public class MyDataSourcePropertiesV3 {

    @NotEmpty private String url;
    @NotEmpty private String username;
    @NotEmpty private String password;
    private Etc etc;

    @Data
    public static class Etc {
        @Min(1) @Max(999) private int maxConnection;
        @DurationMin(seconds = 1) @DurationMax(seconds = 60) private Duration timeout;
        private List<String> options;
    }

}