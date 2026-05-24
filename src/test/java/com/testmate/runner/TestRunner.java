package com.testmate.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Ignore;

@SpringBootTest
@ContextConfiguration(classes = {com.testmate.config.TestConfig.class})
@Ignore("Legacy inventory API sample is not part of the default TestMate AI execution suite.")
@CucumberOptions(
    features = "src/test/resources/inventory.feature",
    glue = "com.testmate",
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
