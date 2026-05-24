package com.testmate.ai.platform.service;

import com.testmate.ai.platform.model.ExecutionStatusResponse;
import com.testmate.ai.platform.model.TestRunRequest;
import com.testmate.ai.platform.model.TestRunResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TestExecutionService {

    private final Map<String, ExecutionStatusResponse> executions = new ConcurrentHashMap<>();

    public TestRunResponse startExecution(TestRunRequest request) {
        String executionId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        ExecutionStatusResponse status = new ExecutionStatusResponse();
        status.setExecutionId(executionId);
        status.setStatus("QUEUED");
        status.setSuite(defaultValue(request.getSuite(), "ai"));
        status.setTags(defaultValue(request.getTags(), "@ai-validation"));
        status.setEnvironment(defaultValue(request.getEnvironment(), "local"));
        status.setCreatedAt(now);
        status.setUpdatedAt(now);
        executions.put(executionId, status);

        TestRunResponse response = new TestRunResponse();
        response.setExecutionId(executionId);
        response.setStatus(status.getStatus());
        response.setSuite(status.getSuite());
        response.setTags(status.getTags());
        response.setEnvironment(status.getEnvironment());
        response.setCreatedAt(now);
        response.setMessage("Execution request accepted. Framework engine integration will be connected in the next iteration.");
        return response;
    }

    public ExecutionStatusResponse getExecution(String executionId) {
        return executions.get(executionId);
    }

    public List<ExecutionStatusResponse> getAllExecutions() {
        return new ArrayList<>(executions.values());
    }

    private String defaultValue(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
