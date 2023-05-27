package com.example.externalconf.external;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CommandLineV1 {

    // 3. 커맨드라인 인수
    // 애플리케이션 실행 시점에 외부 설정값을 main(args) 메서드의 args 파라미터로 전달하는 방법이다.
    // java -jar app.jar dataA dataB 처럼 필요한 데이터를 마지막 위치에 스페이스로 구분해서 전달하면 된다.
    // --> 하지만, key value 형태가 아니기때문에, 편리함이 떨어짐. --> key value 형태로 받고싶으면 직접 파싱해주어야하는 불편함이 있음. --> 이래서 나온게 커맨드라인 옵션 인수 방법임.
    public static void main(String[] args) {
        for (String arg : args) {
            log.info("args {}", arg);
        }
    }
}
