# TestMate AI - Spring Boot Powered AI/API/UI/Mobile Automation Framework

TestMate AI is an enterprise-style Java automation framework for validating AI-powered applications, LLM/chatbot responses, API services, web UI flows, and mobile app flows.

This project is being built in two phases:

1. Spring Boot powered AI/API/UI/Mobile automation framework
2. Spring Boot backend + React dashboard + test execution engine

The current repository focuses on Phase 1.

---

## Current Phase 1 Status

Implemented foundation:

- Java 17 + Maven
- Spring test context for framework configuration
- Cucumber BDD + TestNG runner
- Cucumber Spring integration
- Parallel scenario execution through TestNG DataProvider
- Maven profiles for AI, API, Web, Mobile, and All tests
- RestAssured dependency for API testing
- Playwright Java dependency and Web module skeleton
- Appium Java Client dependency and Mobile module skeleton
- Thread-local scenario/session design
- AI client abstraction with mock provider
- OpenAI and Gemini provider skeletons
- AI response validators
- Cucumber hooks
- Custom JSON and Markdown report generation
- GitHub Actions workflow kept manual-only for now

RAG testing is intentionally not implemented in this version.

---

## Tech Stack

- Java 17
- Spring Boot test context
- Maven
- Cucumber BDD
- TestNG
- RestAssured
- Playwright Java
- Appium Java Client
- Jackson
- Lombok
- SLF4J / Logback
- GitHub Actions

---

## Project Structure

```text
src/test/java/com/testmate/ai
├── clients          # Mock, OpenAI, and Gemini AI client structure
├── config           # Spring/Cucumber config and framework config
├── context          # Thread-local scenario context
├── hooks            # Cucumber hooks
├── mobile           # Appium mobile module skeleton
├── models           # AI request and response models
├── reporting        # Custom report generation
├── runners          # TestNG Cucumber runner with parallel execution
├── stepdefs         # Cucumber step definitions and base steps
├── validators       # AI response validators
└── web              # Playwright web UI module skeleton

src/test/resources
├── config           # Default framework properties
├── features         # Cucumber feature files
└── testdata         # Sample prompt test data
```

---

## Framework Flow

```text
Feature File
   ↓
Cucumber Step Definition
   ↓
Spring/Cucumber Test Context
   ↓
AI/API/Web/Mobile Module
   ↓
Validator or Action Layer
   ↓
Thread-local Scenario Context
   ↓
Cucumber Hooks
   ↓
Cucumber Report + TestMate AI Reports
```

---

## Maven Profiles

Run AI tests:

```bash
mvn clean test -Pai
```

Run API tests:

```bash
mvn clean test -Papi
```

Run Web UI tests:

```bash
mvn clean test -Pweb
```

Run Mobile tests:

```bash
mvn clean test -Pmobile
```

Run all non-manual tests:

```bash
mvn clean test -Pall
```

Run a specific Cucumber tag:

```bash
mvn clean test -Dcucumber.filter.tags="@ai-validation"
```

---

## Parallel Execution

The Cucumber TestNG runner overrides the scenario DataProvider with parallel execution enabled.

```text
AI/API tests: designed for parallel execution
Web UI tests: prepared for thread-local Playwright session management
Mobile tests: prepared for thread-local Appium session management
```

The Maven Surefire thread count is controlled by:

```text
surefire.thread.count=4
```

---

## Reports

Standard Cucumber reports:

```text
target/cucumber-report.html
target/cucumber-report.json
```

Custom TestMate AI reports:

```text
target/testmate-ai-reports/ai-execution-report.json
target/testmate-ai-reports/ai-execution-summary.md
```

---

## GitHub Actions Status

GitHub Actions is intentionally paused for now.

The workflow is manual-only using:

```yaml
on:
  workflow_dispatch:
```

Automatic push and pull request triggers can be re-enabled later after the framework is fully stable.

---

## Phase 1 Completion Criteria

Phase 1 is considered stable when the framework can:

- Run AI tests with mock provider
- Run API tests by profile
- Run Web UI tests by profile
- Run Mobile tests by profile
- Run scenarios in parallel
- Generate Cucumber and custom reports
- Keep GitHub Actions manual until ready
- Keep code modular enough to evolve into a dashboard platform

---

## Phase 2 Future Direction

After Phase 1 is stable, this project can evolve into:

```text
Spring Boot backend + React dashboard + test execution engine
```

Future platform capabilities:

- Trigger test runs from UI
- Select environment, browser, device, and tags
- View live execution status
- View AI/API/UI/Mobile reports
- Store execution history
- Add role-based access
- Add notification integrations

---

## Author

Avinash Kandukuri

Senior SDET | Java Automation Engineer | AI Testing & Automation Framework Specialist
