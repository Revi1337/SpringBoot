package com.example.externalconf.external;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class OsEnv {

    // 1. OS 환경 변수(OS environment variables)는 해당 OS를 사용하는 모든 프로그램에서 읽을 수 있는 설정값이다.
    // --> 한마디로 다른 외부 설정과 비교해서 사용 범위가 가장 넓다.
    // --> OS 환경변수는 다른곳에서도 참조하기때문에, 전역변수의 느낌이 강하다는 단점이 있음.
    public static void main(String[] args) {
        Map<String, String> getenv = System.getenv();
        for (String key : getenv.keySet()) {
            log.info("env {}={}", key, System.getenv(key));
        }
    }

}
