package com.testmate.ai.web;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.testmate.ai.core.config.ConfigReader;

public final class WebSessionManager {

    private static final ThreadLocal<Playwright> PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<Browser> BROWSER = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

    private WebSessionManager() {
    }

    public static Page start() {
        if (PAGE.get() != null) {
            return PAGE.get();
        }

        Playwright playwright = Playwright.create();
        Browser browser = createBrowser(playwright);
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        PLAYWRIGHT.set(playwright);
        BROWSER.set(browser);
        CONTEXT.set(context);
        PAGE.set(page);
        return page;
    }

    public static Page getPage() {
        return PAGE.get();
    }

    public static void close() {
        if (CONTEXT.get() != null) {
            CONTEXT.get().close();
        }
        if (BROWSER.get() != null) {
            BROWSER.get().close();
        }
        if (PLAYWRIGHT.get() != null) {
            PLAYWRIGHT.get().close();
        }
        PAGE.remove();
        CONTEXT.remove();
        BROWSER.remove();
        PLAYWRIGHT.remove();
    }

    public static void clear() {
        close();
    }

    private static Browser createBrowser(Playwright playwright) {
        String browserName = ConfigReader.getOrDefault("web.browser", "chromium");
        boolean headless = Boolean.parseBoolean(ConfigReader.getOrDefault("web.headless", "true"));
        int slowMo = Integer.parseInt(ConfigReader.getOrDefault("web.slow.mo.ms", "0"));

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(slowMo);

        if ("firefox".equalsIgnoreCase(browserName)) {
            return playwright.firefox().launch(options);
        }
        if ("webkit".equalsIgnoreCase(browserName)) {
            return playwright.webkit().launch(options);
        }
        return playwright.chromium().launch(options);
    }
}
