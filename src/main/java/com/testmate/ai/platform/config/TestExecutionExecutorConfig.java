package com.testmate.ai.platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class TestExecutionExecutorConfig {

    @Bean
    public Executor testExecutionExecutor(
            @Value("${app.testmate.execution.core-pool-size:1}") int corePoolSize,
            @Value("${app.testmate.execution.max-pool-size:2}") int maxPoolSize,
            @Value("${app.testmate.execution.queue-capacity:10}") int queueCapacity) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("testmate-exec-");
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}
