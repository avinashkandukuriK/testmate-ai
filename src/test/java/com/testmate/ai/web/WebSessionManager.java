package com.testmate.ai.web;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

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
        WebBrowserConfig config = WebBrowserConfig.fromRuntime();
        BrowserType browserType = browserType(playwright, config.browserName());

        if (config.executionMode() == WebBrowserConfig.ExecutionMode.REMOTE) {
            BrowserType.ConnectOptions options = new BrowserType.ConnectOptions()
                    .setSlowMo(config.slowMoMs())
                    .setTimeout(config.timeoutMs());
            if (!config.remoteHeaders().isEmpty()) {
                options.setHeaders(config.remoteHeaders());
            }
            return browserType.connect(config.remoteEndpoint(), options);
        }

        if (config.executionMode() == WebBrowserConfig.ExecutionMode.CDP) {
            BrowserType.ConnectOverCDPOptions options = new BrowserType.ConnectOverCDPOptions()
                    .setSlowMo(config.slowMoMs())
                    .setTimeout(config.timeoutMs());
            if (!config.remoteHeaders().isEmpty()) {
                options.setHeaders(config.remoteHeaders());
            }
            return playwright.chromium().connectOverCDP(config.remoteEndpoint(), options);
        }

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(config.headless())
                .setSlowMo(config.slowMoMs());

        return browserType.launch(options);
    }

    private static BrowserType browserType(Playwright playwright, String browserName) {
        if ("firefox".equalsIgnoreCase(browserName)) {
            return playwright.firefox();
        }
        if ("webkit".equalsIgnoreCase(browserName)) {
            return playwright.webkit();
        }
        return playwright.chromium();
    }
}
