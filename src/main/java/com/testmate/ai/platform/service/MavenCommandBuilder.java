package com.testmate.ai.platform.service;

import com.testmate.ai.platform.model.TestRunRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Locale;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class MavenCommandBuilder {

    private static final Set<String> ALLOWED_SUITES = Set.of("ai", "api", "web", "mobile", "all");
    private static final Set<String> ALLOWED_EXECUTION_MODES = Set.of("mock", "local", "sauce");
    private static final int MAX_TAG_EXPRESSION_LENGTH = 200;

    public List<String> build(TestRunRequest request) {
        String suite = normalizeSuite(request.getSuite());
        String tags = defaultValue(request.getTags(), defaultTagsForSuite(suite));
        String executionMode = normalizeExecutionMode(request.getExecutionMode());
        validateTags(tags);

        List<String> command = new ArrayList<>();
        command.add(resolveMavenCommand());
        command.add("clean");
        command.add("test");
        command.add("-P" + suite);
        command.add("-Dcucumber.filter.tags=" + tags);
        command.add("-Dai.provider=mock");
        command.add("-Dexecution.mode=" + executionMode);
        return command;
    }

    public String asDisplayCommand(List<String> command) {
        return String.join(" ", command);
    }

    private String resolveMavenCommand() {
        String os = System.getProperty("os.name", "").toLowerCase();
        return os.contains("win") ? "mvn.cmd" : "mvn";
    }

    public String normalizeSuite(String suite) {
        String normalizedSuite = defaultValue(suite, "ai").toLowerCase(Locale.ROOT);
        if (!ALLOWED_SUITES.contains(normalizedSuite)) {
            throw new ResponseStatusException(BAD_REQUEST, "Unsupported suite: " + suite);
        }
        return normalizedSuite;
    }

    public String normalizeExecutionMode(String executionMode) {
        String normalizedExecutionMode = defaultValue(executionMode, "mock").toLowerCase(Locale.ROOT);
        if (!ALLOWED_EXECUTION_MODES.contains(normalizedExecutionMode)) {
            throw new ResponseStatusException(BAD_REQUEST, "Unsupported execution mode: " + executionMode);
        }
        return normalizedExecutionMode;
    }

    public String defaultTagsFor(String suite) {
        return defaultTagsForSuite(normalizeSuite(suite));
    }

    private void validateTags(String tags) {
        if (tags.length() > MAX_TAG_EXPRESSION_LENGTH) {
            throw new ResponseStatusException(BAD_REQUEST, "Tag expression is too long.");
        }
        if (!tags.matches("[@A-Za-z0-9_\\-\\s()&|!~]+")) {
            throw new ResponseStatusException(BAD_REQUEST, "Tag expression contains unsupported characters.");
        }
    }

    private String defaultTagsForSuite(String suite) {
        return switch (suite) {
            case "web" -> "@web";
            case "mobile" -> "@mobile";
            case "api" -> "@api";
            case "all" -> "not @manual";
            default -> "@ai-validation or @ai-safety";
        };
    }

    private String defaultValue(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
