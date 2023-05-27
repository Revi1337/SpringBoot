package com.example.externalconf.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.List;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class CommandLineV2 {

    // 3-1 커맨드라인 옵션 인수 방법. (자바표준 X , 스프링에서 정한 것.)
    // 스프링은 커맨드 라인에 - (dash) 2개( -- )를 연결해서 시작하면 key=value 형식으로 정하고 이것을 커맨드 라인 옵션 인수라 한다.
    // --key=value 형식으로 사용하며, 하나의 키에 여러값도 지정 가능하다. (--username=userA --username=userB) --> -- 가 들어가지않으면 파싱하지 않는다.
    public static void main(String[] args) {
        for (String arg : args) {
            log.info("arg {}", arg);
        }
        ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
        log.info("SourceArgs = {}", List.of(applicationArguments.getSourceArgs())); // 모든 옵션들 출력
        log.info("NonOptionsArgs = {}", applicationArguments.getNonOptionArgs()); // -- 들어가지 않은 것 출력
        log.info("OptionsNames = {}", applicationArguments.getOptionNames()); // -- 들어간것 모두 출력 (파싱 후)

        Set<String> optionNames = applicationArguments.getOptionNames();
        for (String optionName : optionNames) {
            log.info("option arg {}={}", optionName, applicationArguments.getOptionValues(optionName));
        }
    }
}
