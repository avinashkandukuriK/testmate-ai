package com.testmate.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {com.testmate.config.TestConfig.class})
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.testmate.stepdefs",
    plugin = {
        "pretty",
        "json:target/cucumber-report.json",
        "html:target/cucumber-html-report",
        "timeline:target/cucumber-timeline-report",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true,
    tags = "@smoke"
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
