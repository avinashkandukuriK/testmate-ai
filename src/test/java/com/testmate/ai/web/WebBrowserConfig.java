package com.testmate.ai.web;

import com.testmate.ai.core.config.ConfigReader;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public record WebBrowserConfig(
        ExecutionMode executionMode,
        String browserName,
        boolean headless,
        int slowMoMs,
        int timeoutMs,
        String remoteEndpoint,
        Map<String, String> remoteHeaders) {

    private static final String DEFAULT_BROWSER = "chromium";
    private static final int DEFAULT_TIMEOUT_MS = 30000;

    public enum ExecutionMode {
        LOCAL,
        REMOTE,
        CDP
    }

    public static WebBrowserConfig fromRuntime() {
        return from(new ConfigSource() {
            @Override
            public String get(String key) {
                return ConfigReader.get(key);
            }
        });
    }

    static WebBrowserConfig from(Properties properties) {
        return from(properties::getProperty);
    }

    private static WebBrowserConfig from(ConfigSource source) {
        ExecutionMode executionMode = parseExecutionMode(value(source, "web.execution.mode", "local"));
        String browserName = value(source, "web.browser", DEFAULT_BROWSER).toLowerCase(Locale.ROOT);
        boolean headless = Boolean.parseBoolean(value(source, "web.headless", "true"));
        int slowMoMs = parseInt(value(source, "web.slow.mo.ms", "0"), "web.slow.mo.ms");
        int timeoutMs = parseInt(value(source, "web.remote.connect.timeout.ms",
                String.valueOf(DEFAULT_TIMEOUT_MS)), "web.remote.connect.timeout.ms");
        String endpoint = resolveEndpoint(source, executionMode);
        Map<String, String> headers = parseHeaders(value(source, "web.remote.headers", ""));

        validate(executionMode, browserName, endpoint);

        return new WebBrowserConfig(
                executionMode,
                browserName,
                headless,
                slowMoMs,
                timeoutMs,
                endpoint,
                headers);
    }

    private static ExecutionMode parseExecutionMode(String value) {
        return switch (value.toLowerCase(Locale.ROOT)) {
            case "local", "container" -> ExecutionMode.LOCAL;
            case "remote" -> ExecutionMode.REMOTE;
            case "cdp", "chromium-cdp" -> ExecutionMode.CDP;
            default -> throw new IllegalArgumentException("Unsupported web.execution.mode: " + value);
        };
    }

    private static String resolveEndpoint(ConfigSource source, ExecutionMode executionMode) {
        if (executionMode == ExecutionMode.REMOTE) {
            return value(source, "web.remote.ws.endpoint", "");
        }
        if (executionMode == ExecutionMode.CDP) {
            return value(source, "web.remote.cdp.endpoint", "");
        }
        return "";
    }

    private static void validate(ExecutionMode executionMode, String browserName, String endpoint) {
        if (executionMode == ExecutionMode.REMOTE && endpoint.isBlank()) {
            throw new IllegalArgumentException("web.remote.ws.endpoint is required when web.execution.mode=remote");
        }
        if (executionMode == ExecutionMode.CDP && endpoint.isBlank()) {
            throw new IllegalArgumentException("web.remote.cdp.endpoint is required when web.execution.mode=cdp");
        }
        if (executionMode == ExecutionMode.CDP && !"chromium".equals(browserName)) {
            throw new IllegalArgumentException("CDP remote execution is only supported with chromium");
        }
    }

    private static Map<String, String> parseHeaders(String rawHeaders) {
        if (rawHeaders == null || rawHeaders.isBlank()) {
            return Collections.emptyMap();
        }

        Map<String, String> headers = new LinkedHashMap<>();
        for (String header : rawHeaders.split(",")) {
            String[] parts = header.split("=", 2);
            if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
                throw new IllegalArgumentException("web.remote.headers must use comma-separated key=value pairs");
            }
            headers.put(parts[0].trim(), parts[1].trim());
        }
        return Collections.unmodifiableMap(headers);
    }

    private static int parseInt(String value, String key) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(key + " must be a number", e);
        }
    }

    private static String value(ConfigSource source, String key, String defaultValue) {
        String value = source.get(key);
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }

    private interface ConfigSource {
        String get(String key);
    }
}
