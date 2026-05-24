package com.testmate.ai.platform.repository;

import com.testmate.ai.platform.entity.TestExecutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestExecutionRepository extends JpaRepository<TestExecutionEntity, String> {
}
