package com.testmate.ai.platform.repository;

import com.testmate.ai.platform.entity.ExecutionLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionLogRepository extends JpaRepository<ExecutionLogEntry, Long> {

    List<ExecutionLogEntry> findByExecutionIdOrderByCreatedAtAsc(String executionId);
}
