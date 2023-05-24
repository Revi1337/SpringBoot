package com.example.autoconfig.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;


@Slf4j
public class MemoryCondition implements Condition {
// 1. @Conditional 어노테이션을 사용하기위해서는 Condition 인터페이스를 구현하여야 함.

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 2. #java -Dmemory=on -jar project.jar 처럼 -Dmemory=on 를 사용했을떄만 실행되도록 구성
        String memory = context.getEnvironment().getProperty("memory");
        log.info("memory = {}", memory);
        return "on".equals(memory);
    }
}
