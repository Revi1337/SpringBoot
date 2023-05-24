package com.example.autoconfig.config;

import com.example.memory.MemoryController;
import com.example.memory.MemoryFinder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Conditional(MemoryCondition.class) // -Dmemory=on 를 사용했을떄만 실행되도록 구성
@ConditionalOnProperty(name = "memory", havingValue = "on") // 환경정보가 memory=on 이라는 조건에 맞으면 동작하고 그렇지 않으면 동작하지 않음.
public class MemoryConfig {

    @Bean
    public MemoryController memoryController() {
        return new MemoryController(memoryFinder());
    }

    @Bean
    public MemoryFinder memoryFinder() {
        return new MemoryFinder();
    }
}
