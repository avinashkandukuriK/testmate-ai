package com.testmate.ai.core.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = TestMateSpringConfig.class)
public class CucumberSpringConfig {
}
