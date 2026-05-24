package com.testmate.ai.platform.service;

import com.testmate.ai.platform.entity.ExecutionLogEntry;
import com.testmate.ai.platform.entity.TestExecutionEntity;
import com.testmate.ai.platform.model.ExecutionStatusResponse;
import com.testmate.ai.platform.model.TestRunRequest;
import com.testmate.ai.platform.model.TestRunResponse;
import com.testmate.ai.platform.repository.ExecutionLogRepository;
import com.testmate.ai.platform.repository.TestExecutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@Service
public class TestExecutionService {

    private final Map<String, ExecutionStatusResponse> executions = new ConcurrentHashMap<>();
    private final MavenCommandBuilder commandBuilder;
    private final TestExecutionRepository executionRepository;
    private final ExecutionLogRepository logRepository;
    private final Executor testExecutionExecutor;

    public TestExecutionService(MavenCommandBuilder commandBuilder,
                                TestExecutionRepository executionRepository,
                                ExecutionLogRepository logRepository,
                                Executor testExecutionExecutor) {
        this.commandBuilder = commandBuilder;
        this.executionRepository = executionRepository;
        this.logRepository = logRepository;
        this.testExecutionExecutor = testExecutionExecutor;
    }

    public TestRunResponse startExecution(TestRunRequest request) {
        String executionId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        List<String> command = commandBuilder.build(request);
        String suite = commandBuilder.normalizeSuite(request.getSuite());
        String executionMode = commandBuilder.normalizeExecutionMode(request.getExecutionMode());
        String tags = defaultValue(request.getTags(), commandBuilder.defaultTagsFor(suite));

        ExecutionStatusResponse status = new ExecutionStatusResponse();
        status.setExecutionId(executionId);
        status.setStatus("QUEUED");
        status.setSuite(suite);
        status.setTags(tags);
        status.setEnvironment(defaultValue(request.getEnvironment(), "local"));
        status.setExecutionMode(executionMode);
        status.setCommand(commandBuilder.asDisplayCommand(command));
        status.setReportPath("target/cucumber-report.html");
        status.setCreatedAt(now);
        status.setUpdatedAt(now);
        saveStatus(status);
        appendLog(executionId, "Execution queued: " + status.getCommand());

        try {
            CompletableFuture.runAsync(() -> runProcess(executionId, command), testExecutionExecutor);
        } catch (RuntimeException e) {
            status.setStatus("REJECTED");
            status.setUpdatedAt(LocalDateTime.now());
            saveStatus(status);
            appendLog(executionId, "Execution rejected: executor queue is full.");
            throw new ResponseStatusException(TOO_MANY_REQUESTS, "Too many test executions are already queued.");
        }

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
        ExecutionStatusResponse cached = executions.get(executionId);
        if (cached != null) {
            return cached;
        }
        return executionRepository.findById(executionId).map(this::toResponse).orElse(null);
    }

    public List<ExecutionStatusResponse> getAllExecutions() {
        return executionRepository.findAll().stream()
                .map(this::toResponse)
                .sorted(Comparator.comparing(ExecutionStatusResponse::getCreatedAt).reversed())
                .toList();
    }

    public List<String> getExecutionLogs(String executionId) {
        return logRepository.findByExecutionIdOrderByCreatedAtAsc(executionId).stream()
                .map(log -> log.getCreatedAt() + " | " + log.getContent())
                .toList();
    }

    private void runProcess(String executionId, List<String> command) {
        ExecutionStatusResponse status = getExecution(executionId);
        if (status == null) {
            return;
        }

        status.setStatus("RUNNING");
        status.setStartedAt(LocalDateTime.now());
        status.setUpdatedAt(LocalDateTime.now());
        saveStatus(status);
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
            saveStatus(status);
        }
    }

    private void saveStatus(ExecutionStatusResponse response) {
        executions.put(response.getExecutionId(), response);
        executionRepository.save(toEntity(response));
    }

    private void appendLog(String executionId, String logLine) {
        ExecutionLogEntry entry = new ExecutionLogEntry();
        entry.setExecutionId(executionId);
        entry.setCreatedAt(LocalDateTime.now());
        entry.setContent(logLine);
        logRepository.save(entry);
    }

    private TestExecutionEntity toEntity(ExecutionStatusResponse response) {
        TestExecutionEntity entity = new TestExecutionEntity();
        entity.setExecutionId(response.getExecutionId());
        entity.setStatus(response.getStatus());
        entity.setSuite(response.getSuite());
        entity.setTags(response.getTags());
        entity.setEnvironment(response.getEnvironment());
        entity.setExecutionMode(response.getExecutionMode());
        entity.setCommand(response.getCommand());
        entity.setExitCode(response.getExitCode());
        entity.setReportPath(response.getReportPath());
        entity.setCreatedAt(response.getCreatedAt());
        entity.setStartedAt(response.getStartedAt());
        entity.setFinishedAt(response.getFinishedAt());
        entity.setUpdatedAt(response.getUpdatedAt());
        return entity;
    }

    private ExecutionStatusResponse toResponse(TestExecutionEntity entity) {
        ExecutionStatusResponse response = new ExecutionStatusResponse();
        response.setExecutionId(entity.getExecutionId());
        response.setStatus(entity.getStatus());
        response.setSuite(entity.getSuite());
        response.setTags(entity.getTags());
        response.setEnvironment(entity.getEnvironment());
        response.setExecutionMode(entity.getExecutionMode());
        response.setCommand(entity.getCommand());
        response.setExitCode(entity.getExitCode());
        response.setReportPath(entity.getReportPath());
        response.setCreatedAt(entity.getCreatedAt());
        response.setStartedAt(entity.getStartedAt());
        response.setFinishedAt(entity.getFinishedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    private String defaultValue(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
