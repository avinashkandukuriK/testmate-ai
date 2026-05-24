package com.testmate.ai.stepdefs;

import com.testmate.ai.mobile.MobileSessionManager;
import com.testmate.ai.mobile.ScreenObjectManager;
import com.testmate.ai.mobile.screens.SampleMobileScreen;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class MobileSampleSteps {

    private final SampleMobileScreen sampleScreen = ScreenObjectManager.getInstance().sampleMobileScreen();

    @Given("the mobile sample is configured")
    public void mobileSampleConfigured() {
        Assert.assertNotNull(sampleScreen);
    }

    @When("I start the mobile sample")
    public void startMobileSample() {
        sampleScreen.open();
    }

    @Then("the mobile sample should be active")
    public void mobileSampleShouldBeActive() {
        Assert.assertTrue(sampleScreen.isActive());
    }

    @Then("the mobile screen should be {string}")
    public void mobileScreenShouldBe(String expectedScreen) {
        Assert.assertEquals(sampleScreen.currentScreen(), expectedScreen);
    }

    @After("@mobile")
    public void cleanupMobile() {
        MobileSessionManager.close();
        ScreenObjectManager.remove();
    }
}
