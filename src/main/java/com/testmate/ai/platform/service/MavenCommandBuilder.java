package com.testmate.ai.platform.service;

import com.testmate.ai.platform.model.TestRunRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MavenCommandBuilder {

    public List<String> build(TestRunRequest request) {
        String suite = defaultValue(request.getSuite(), "ai").toLowerCase();
        String tags = defaultValue(request.getTags(), defaultTagsForSuite(suite));
        String executionMode = defaultValue(request.getExecutionMode(), "mock");

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
