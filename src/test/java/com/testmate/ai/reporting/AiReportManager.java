package com.testmate.ai.reporting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.testmate.ai.core.config.FrameworkConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AiReportManager {

    private static final List<AiScenarioResult> RESULTS = Collections.synchronizedList(new ArrayList<>());
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private AiReportManager() {
    }

    public static void addResult(AiScenarioResult result) {
        RESULTS.add(result);
    }

    public static List<AiScenarioResult> getResults() {
        return new ArrayList<>(RESULTS);
    }

    public static void flushReports() {
        try {
            Path reportDir = Path.of(FrameworkConfig.getReportOutputDir());
            Files.createDirectories(reportDir);
            writeJsonReport(reportDir.resolve("ai-execution-report.json"));
            writeMarkdownReport(reportDir.resolve("ai-execution-summary.md"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to write AI execution reports", e);
        }
    }

    private static void writeJsonReport(Path path) throws IOException {
        OBJECT_MAPPER.writeValue(path.toFile(), RESULTS);
    }

    private static void writeMarkdownReport(Path path) throws IOException {
        StringBuilder report = new StringBuilder();
        report.append("# TestMate AI Execution Summary\n\n");
        report.append("Generated At: ").append(LocalDateTime.now()).append("\n\n");
        report.append("| Scenario | Status | Provider | Model | Response Time |\n");
        report.append("|---|---|---|---|---|\n");

        for (AiScenarioResult result : RESULTS) {
            report.append("| ")
                    .append(safe(result.getScenarioName()))
                    .append(" | ")
                    .append(safe(result.getStatus()))
                    .append(" | ")
                    .append(safe(result.getProvider()))
                    .append(" | ")
                    .append(safe(result.getModel()))
                    .append(" | ")
                    .append(result.getResponseTimeMs())
                    .append(" ms |\n");
        }

        Files.writeString(path, report.toString());
    }

    private static String safe(String value) {
        if (value == null || value.isBlank()) {
            return "N/A";
        }
        return value.replace("|", "\\|").replace("\n", " ");
    }
}
