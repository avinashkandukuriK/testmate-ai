package com.testmate.ai.config;

import com.testmate.ai.core.config.TestMateSpringConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = TestMateSpringConfig.class)
public class CucumberSpringConfig {
}
