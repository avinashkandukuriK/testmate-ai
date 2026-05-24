package com.testmate.ai.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import com.testmate.ai.web.BasePage;

public class ParaBankPage extends BasePage {

    private static final String DEFAULT_BASE_URL = "https://parabank.parasoft.com/parabank";

    public void openHome(String baseUrl) {
        open(baseUrl, "index.htm");
    }

    public void openServices(String baseUrl) {
        open(baseUrl, "services.htm");
    }

    public void openRegistration(String baseUrl) {
        open(baseUrl, "register.htm");
    }

    public void openCustomerLookup(String baseUrl) {
        open(baseUrl, "lookup.htm");
    }

    public boolean isCustomerLoginPanelVisible() {
        return isVisible("h2:has-text('Customer Login')")
                && isVisible("input[name='username']")
                && isVisible("input[name='password']")
                && isVisible("input[value='Log In']");
    }

    public boolean arePublicServiceCategoriesVisible() {
        return isVisible("li.captionone:has-text('ATM Services')")
                && isVisible("li.captiontwo:has-text('Online Services')")
                && isVisible("a:has-text('Bill Pay')")
                && isVisible("a:has-text('Transfer Funds')");
    }

    public boolean listsBankingServiceEndpoints() {
        return isVisible("span.heading:has-text('Available ParaBank SOAP services')")
                && isVisible("span.heading:has-text('Available RESTful services')")
                && isVisible("span.porttypename:has-text('ParaBankService')")
                && isVisible("a[href*='services/ParaBank?wsdl']")
                && isVisible("a[href*='services/bank?_wadl']");
    }

    public boolean hasRequiredRegistrationFields() {
        return isVisible("#customerForm")
                && isVisible("#customer\\.firstName")
                && isVisible("#customer\\.lastName")
                && isVisible("#customer\\.address\\.street")
                && isVisible("#customer\\.address\\.city")
                && isVisible("#customer\\.address\\.state")
                && isVisible("#customer\\.address\\.zipCode")
                && isVisible("#customer\\.ssn")
                && isVisible("#customer\\.username")
                && isVisible("#customer\\.password")
                && isVisible("#repeatedPassword")
                && isVisible("input[value='Register']");
    }

    public boolean hasIdentityVerificationFields() {
        return isVisible("#lookupForm")
                && isVisible("#firstName")
                && isVisible("#lastName")
                && isVisible("#address\\.street")
                && isVisible("#address\\.city")
                && isVisible("#address\\.state")
                && isVisible("#address\\.zipCode")
                && isVisible("#ssn")
                && isVisible("input[value='Find My Login Info']");
    }

    private void open(String baseUrl, String path) {
        String normalizedBase = normalizeBaseUrl(baseUrl);
        page().navigate(normalizedBase + "/" + path);
        page().waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    private String normalizeBaseUrl(String baseUrl) {
        String value = baseUrl == null || baseUrl.isBlank() ? DEFAULT_BASE_URL : baseUrl.trim();
        return value.endsWith("/") ? value.substring(0, value.length() - 1) : value;
    }

    private boolean isVisible(String selector) {
        Locator locator = page().locator(selector).first();
        return locator.count() > 0 && locator.isVisible();
    }
}
