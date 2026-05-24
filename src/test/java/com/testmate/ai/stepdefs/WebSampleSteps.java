package com.testmate.ai.stepdefs;

import com.testmate.ai.core.config.ConfigReader;
import com.testmate.ai.web.PageObjectManager;
import com.testmate.ai.web.WebSessionManager;
import com.testmate.ai.web.pages.SampleWebPage;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class WebSampleSteps {

    private final SampleWebPage sampleWebPage = PageObjectManager.getInstance().sampleWebPage();
    private String capturedTitle;

    @Given("the web test application is configured")
    public void theWebTestApplicationIsConfigured() {
        String baseUrl = ConfigReader.getOrDefault("web.base.url", "https://example.com");
        Assert.assertFalse(baseUrl.isBlank(), "web.base.url must be configured");
    }

    @When("I open the configured web page")
    public void iOpenTheConfiguredWebPage() {
        String baseUrl = ConfigReader.getOrDefault("web.base.url", "https://example.com");
        sampleWebPage.open(baseUrl);
        capturedTitle = sampleWebPage.getPageTitle();
    }

    @Then("the web page title should not be empty")
    public void theWebPageTitleShouldNotBeEmpty() {
        Assert.assertNotNull(capturedTitle, "Web page title should not be null");
        Assert.assertFalse(capturedTitle.isBlank(), "Web page title should not be blank");
    }

    @After("@web")
    public void closeWebSession() {
        WebSessionManager.close();
        PageObjectManager.remove();
    }
}
