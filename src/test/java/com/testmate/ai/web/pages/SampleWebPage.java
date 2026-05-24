package com.testmate.ai.web.pages;

import com.testmate.ai.web.BasePage;

public class SampleWebPage extends BasePage {

    public void open(String url) {
        page().navigate(url);
    }

    public String getPageTitle() {
        return title();
    }

    public String getCurrentUrl() {
        return url();
    }
}
