package com.testmate.ai.platform.service;

import com.testmate.ai.platform.model.ExecutionStatusResponse;
import com.testmate.ai.platform.model.TestRunRequest;
import com.testmate.ai.platform.model.TestRunResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TestExecutionService {

    private final Map<String, ExecutionStatusResponse> executions = new ConcurrentHashMap<>();
    private final Map<String, List<String>> executionLogs = new ConcurrentHashMap<>();
    private final MavenCommandBuilder commandBuilder;

    public TestExecutionService(MavenCommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    public TestRunResponse startExecution(TestRunRequest request) {
        String executionId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        List<String> command = commandBuilder.build(request);

        ExecutionStatusResponse status = new ExecutionStatusResponse();
        status.setExecutionId(executionId);
        status.setStatus("QUEUED");
        status.setSuite(defaultValue(request.getSuite(), "ai"));
        status.setTags(defaultValue(request.getTags(), "@ai-validation or @ai-safety"));
        status.setEnvironment(defaultValue(request.getEnvironment(), "local"));
        status.setExecutionMode(defaultValue(request.getExecutionMode(), "mock"));
        status.setCommand(commandBuilder.asDisplayCommand(command));
        status.setReportPath("target/cucumber-report.html");
        status.setCreatedAt(now);
        status.setUpdatedAt(now);
        executions.put(executionId, status);
        executionLogs.put(executionId, Collections.synchronizedList(new ArrayList<>()));
        appendLog(executionId, "Execution queued: " + status.getCommand());

        CompletableFuture.runAsync(() -> runProcess(executionId, command));

        TestRunResponse response = new TestRunResponse();
        response.setExecutionId(executionId);
        response.setStatus(status.getStatus());
        response.setSuite(status.getSuite());
        response.setTags(status.getTags());
        response.setEnvironment(status.getEnvironment());
        response.setCreatedAt(now);
        response.setMessage("Execution request accepted and started asynchronously.");
        return response;
    }

    public ExecutionStatusResponse getExecution(String executionId) {
        return executions.get(executionId);
    }

    public List<ExecutionStatusResponse> getAllExecutions() {
        return new ArrayList<>(executions.values());
    }

    public List<String> getExecutionLogs(String executionId) {
        return executionLogs.getOrDefault(executionId, List.of());
    }

    private void runProcess(String executionId, List<String> command) {
        ExecutionStatusResponse status = executions.get(executionId);
        if (status == null) {
            return;
        }

        status.setStatus("RUNNING");
        status.setStartedAt(LocalDateTime.now());
        status.setUpdatedAt(LocalDateTime.now());
        appendLog(executionId, "Execution started.");

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    appendLog(executionId, line);
                }
            }

            int exitCode = process.waitFor();
            status.setExitCode(exitCode);
            status.setStatus(exitCode == 0 ? "PASSED" : "FAILED");
            appendLog(executionId, "Execution finished with exit code: " + exitCode);
        } catch (Exception e) {
            status.setStatus("ERROR");
            appendLog(executionId, "Execution error: " + e.getMessage());
        } finally {
            status.setFinishedAt(LocalDateTime.now());
            status.setUpdatedAt(LocalDateTime.now());
        }
    }

    private void appendLog(String executionId, String logLine) {
        executionLogs.computeIfAbsent(executionId, key -> Collections.synchronizedList(new ArrayList<>()))
                .add(LocalDateTime.now() + " | " + logLine);
    }

    private String defaultValue(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
