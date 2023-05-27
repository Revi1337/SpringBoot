package com.example.externalconf.external;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class JavaSystemProperties {

    // 2. 자바 시스템 속성
    // 자바 시스템 속성(Java System properties)은 실행한 JVM 안에서 접근 가능한 외부 설정이다.
    // 자바가 제공하는 기본적인 정보들이 들어있음.
    // --> 1. 자바 시스템 속성을 외부에서 주는 java -D 방법이있고,
    // --> 2. 자바 시스템 속성을 자바코드에서 설정하는 방법이 있다. --> 이 방법은 자바 코드안에서 사용하는 것이기 때문에 설정을 분리하는 효과는 없음.
    public static void main(String[] args) {
        Properties properties = System.getProperties();
        for (Object key : properties.keySet()) {
            log.info("prop {}={}", key, System.getProperty(String.valueOf(key)));
        }

        // 1. -D VM 옵션을 통해서 key=value 형식을 주면 된다. 이 예제는 url=dev 속성이 추가된다.
        String url = System.getProperty("url");
        String username = System.getProperty("username");
        String password = System.getProperty("password");
        log.info("url={}", url);
        log.info("username={}", username);
        log.info("password={}", password);

        // 2. 자바 시스템 속성을 자바코드에서 설정하는 방법이 있다. --> 이 방법은 자바 코드안에서 사용하는 것이기 때문에 설정을 분리하는 효과는 없음.
        System.setProperty("hello_key", "hello_value");
        Properties properties1 = System.getProperties();
        System.out.println("==============================================");
        for (Object key : properties1.keySet()) {
            log.info("prop {}={}", key, System.getProperty(String.valueOf(key)));
        }
    }
}

