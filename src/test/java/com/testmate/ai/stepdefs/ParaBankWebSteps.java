package com.testmate.ai.stepdefs;

import com.testmate.ai.core.config.ConfigReader;
import com.testmate.ai.web.PageObjectManager;
import com.testmate.ai.web.pages.ParaBankPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class ParaBankWebSteps {

    private final ParaBankPage paraBankPage = PageObjectManager.getInstance().paraBankPage();
    private String baseUrl;

    @Given("ParaBank is configured as the web test application")
    public void parabankIsConfiguredAsTheWebTestApplication() {
        baseUrl = ConfigReader.getOrDefault("parabank.base.url", "https://parabank.parasoft.com/parabank");
        Assert.assertTrue(baseUrl.startsWith("https://"), "ParaBank base URL should use HTTPS");
    }

    @When("I open the ParaBank home page")
    public void iOpenTheParaBankHomePage() {
        paraBankPage.openHome(baseUrl);
    }

    @When("I open the ParaBank services page")
    public void iOpenTheParaBankServicesPage() {
        paraBankPage.openServices(baseUrl);
    }

    @When("I open the ParaBank registration page")
    public void iOpenTheParaBankRegistrationPage() {
        paraBankPage.openRegistration(baseUrl);
    }

    @When("I open the ParaBank customer lookup page")
    public void iOpenTheParaBankCustomerLookupPage() {
        paraBankPage.openCustomerLookup(baseUrl);
    }

    @Then("the ParaBank customer login panel should be visible")
    public void theParaBankCustomerLoginPanelShouldBeVisible() {
        Assert.assertTrue(paraBankPage.isCustomerLoginPanelVisible(), "Customer login panel should be visible");
    }

    @Then("the ParaBank public service categories should be visible")
    public void theParaBankPublicServiceCategoriesShouldBeVisible() {
        Assert.assertTrue(paraBankPage.arePublicServiceCategoriesVisible(), "Public service categories should be visible");
    }

    @Then("the ParaBank page title should contain {string}")
    public void theParaBankPageTitleShouldContain(String expectedTitleText) {
        Assert.assertTrue(paraBankPage.title().contains(expectedTitleText),
                "Page title should contain: " + expectedTitleText);
    }

    @Then("the ParaBank services page should list banking service endpoints")
    public void theParaBankServicesPageShouldListBankingServiceEndpoints() {
        Assert.assertTrue(paraBankPage.listsBankingServiceEndpoints(),
                "Services page should list SOAP and REST banking endpoints");
    }

    @Then("the ParaBank registration form should contain required customer fields")
    public void theParaBankRegistrationFormShouldContainRequiredCustomerFields() {
        Assert.assertTrue(paraBankPage.hasRequiredRegistrationFields(),
                "Registration form should contain required customer fields");
    }

    @Then("the ParaBank customer lookup form should contain identity verification fields")
    public void theParaBankCustomerLookupFormShouldContainIdentityVerificationFields() {
        Assert.assertTrue(paraBankPage.hasIdentityVerificationFields(),
                "Customer lookup form should contain identity verification fields");
    }
}
