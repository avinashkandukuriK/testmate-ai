package com.testmate.ai.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Properties;

public class WebBrowserConfigTest {

    @Test
    public void localConfigUsesLaunchDefaults() {
        WebBrowserConfig config = WebBrowserConfig.from(properties(Map.of()));

        Assert.assertEquals(config.executionMode(), WebBrowserConfig.ExecutionMode.LOCAL);
        Assert.assertEquals(config.browserName(), "chromium");
        Assert.assertTrue(config.headless());
        Assert.assertEquals(config.slowMoMs(), 0);
        Assert.assertEquals(config.timeoutMs(), 30000);
        Assert.assertTrue(config.remoteEndpoint().isEmpty());
        Assert.assertTrue(config.remoteHeaders().isEmpty());
    }

    @Test
    public void remoteConfigRequiresPlaywrightWebSocketEndpoint() {
        WebBrowserConfig config = WebBrowserConfig.from(properties(Map.of(
                "web.execution.mode", "remote",
                "web.browser", "firefox",
                "web.remote.ws.endpoint", "wss://grid.example.com/playwright",
                "web.remote.connect.timeout.ms", "45000",
                "web.remote.headers", "Authorization=Bearer token, X-Build-Id=build-42"
        )));

        Assert.assertEquals(config.executionMode(), WebBrowserConfig.ExecutionMode.REMOTE);
        Assert.assertEquals(config.browserName(), "firefox");
        Assert.assertEquals(config.remoteEndpoint(), "wss://grid.example.com/playwright");
        Assert.assertEquals(config.timeoutMs(), 45000);
        Assert.assertEquals(config.remoteHeaders().get("Authorization"), "Bearer token");
        Assert.assertEquals(config.remoteHeaders().get("X-Build-Id"), "build-42");
    }

    @Test
    public void cdpConfigUsesChromiumEndpoint() {
        WebBrowserConfig config = WebBrowserConfig.from(properties(Map.of(
                "web.execution.mode", "cdp",
                "web.remote.cdp.endpoint", "http://browser.example.com:9222"
        )));

        Assert.assertEquals(config.executionMode(), WebBrowserConfig.ExecutionMode.CDP);
        Assert.assertEquals(config.browserName(), "chromium");
        Assert.assertEquals(config.remoteEndpoint(), "http://browser.example.com:9222");
    }

    @Test
    public void remoteModeFailsWithoutEndpoint() {
        IllegalArgumentException exception = Assert.expectThrows(IllegalArgumentException.class,
                () -> WebBrowserConfig.from(properties(Map.of("web.execution.mode", "remote"))));

        Assert.assertTrue(exception.getMessage().contains("web.remote.ws.endpoint"));
    }

    @Test
    public void cdpModeRejectsNonChromiumBrowser() {
        IllegalArgumentException exception = Assert.expectThrows(IllegalArgumentException.class,
                () -> WebBrowserConfig.from(properties(Map.of(
                        "web.execution.mode", "cdp",
                        "web.browser", "webkit",
                        "web.remote.cdp.endpoint", "http://browser.example.com:9222"
                ))));

        Assert.assertTrue(exception.getMessage().contains("CDP"));
    }

    private static Properties properties(Map<String, String> values) {
        Properties properties = new Properties();
        values.forEach(properties::setProperty);
        return properties;
    }
}
