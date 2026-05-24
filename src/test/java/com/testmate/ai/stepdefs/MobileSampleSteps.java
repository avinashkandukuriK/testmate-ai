package com.testmate.ai.stepdefs;

import com.testmate.ai.mobile.MobileActions;
import com.testmate.ai.mobile.MobileSessionManager;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class MobileSampleSteps {

    private final MobileActions mobileActions = new MobileActions();

    @Given("the mobile sample is configured")
    public void theMobileSampleIsConfigured() {
        Assert.assertNotNull(mobileActions, "Mobile actions should be initialized");
    }

    @When("I start the mobile sample")
    public void iStartTheMobileSample() {
        mobileActions.openApplication();
    }

    @Then("the mobile sample should be active")
    public void theMobileSampleShouldBeActive() {
        Assert.assertTrue(mobileActions.isApplicationOpen(), "Mobile sample should be active");
    }

    @Then("the mobile screen should be {string}")
    public void theMobileScreenShouldBe(String expectedScreen) {
        Assert.assertEquals(mobileActions.currentScreen(), expectedScreen);
    }

    @After("@mobile")
    public void closeMobileSession() {
        MobileSessionManager.close();
    }
}
