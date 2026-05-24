package com.testmate.ai.platform.controller;

import com.testmate.ai.platform.model.ExecutionStatusResponse;
import com.testmate.ai.platform.model.TestRunRequest;
import com.testmate.ai.platform.model.TestRunResponse;
import com.testmate.ai.platform.service.TestExecutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/executions")
public class TestExecutionController {

    private final TestExecutionService testExecutionService;

    public TestExecutionController(TestExecutionService testExecutionService) {
        this.testExecutionService = testExecutionService;
    }

    @PostMapping
    public ResponseEntity<TestRunResponse> startExecution(@RequestBody TestRunRequest request) {
        return ResponseEntity.accepted().body(testExecutionService.startExecution(request));
    }

    @GetMapping("/{executionId}")
    public ResponseEntity<ExecutionStatusResponse> getExecution(@PathVariable String executionId) {
        ExecutionStatusResponse response = testExecutionService.getExecution(executionId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ExecutionStatusResponse>> getExecutions() {
        return ResponseEntity.ok(testExecutionService.getAllExecutions());
    }
}
