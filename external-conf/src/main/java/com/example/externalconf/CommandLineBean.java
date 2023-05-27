package com.example.externalconf;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component @Slf4j @RequiredArgsConstructor
public class CommandLineBean {

    // 스프링 부트는 커맨드 라인을 포함해서 커맨드라인 옵션 인수를 활용할 수 있는  ApplicationArguments 를 Bean 으로 등록해둔다.
    // 그리고 그 안에 입력한 커맨드 라인을 저장해둔다. 그래서 해당 빈을 주입받으면 커맨드 라인으로 입력한 값을 어디서든 사용할 수 있다.

    private final ApplicationArguments applicationArguments;

    @PostConstruct
    public void init() {
        log.info("source {}", List.of(applicationArguments.getSourceArgs()));
        log.info("optionNames {}", applicationArguments.getOptionNames());
        Set<String> optionNames = applicationArguments.getOptionNames();
        for (String optionName : optionNames) {
            log.info("option args {}={}", optionName, applicationArguments.getOptionValues(optionName));
        }
    }

}
