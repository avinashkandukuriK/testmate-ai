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
- Playwright Java dependency with runnable Web sample
- Appium Java Client dependency with runnable Mobile mock sample
- Mobile execution options for mock, local Appium, and Sauce Labs placeholders
- Thread-local scenario/session design
- Page Object Model for Web UI
- Screen Object Model for Mobile
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

## Enterprise Project Structure

```text
src/test/java/com/testmate/ai
├── core
│   └── config       # Spring/Cucumber config, framework config, config reader
├── clients          # AI client contract and provider skeletons
├── context          # Thread-local scenario context
├── hooks            # Cucumber lifecycle hooks
├── mobile
│   ├── screens      # Screen Object Model classes
│   ├── ScreenObjectManager
│   ├── MobileSessionManager
│   ├── MockMobileDriver
│   ├── LocalMobileDriverFactory
│   └── SauceLabsMobileDriverFactory
├── models           # AI request and response models
├── reporting        # Custom report generation
├── runners          # TestNG Cucumber runner with parallel execution
├── stepdefs         # Cucumber step definitions
├── validators       # AI response validators
└── web
    ├── pages        # Page Object Model classes
    ├── PageObjectManager
    ├── WebSessionManager
    └── WebActions

src/test/resources
├── config           # Default framework properties
├── features         # Cucumber feature files for AI, Web, and Mobile samples
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
PageObjectManager / ScreenObjectManager / AI Client Factory
   ↓
Page Object / Screen Object / Validator Layer
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

Run Web UI sample tests:

```bash
mvn clean test -Pweb
```

Run Mobile sample tests in default mock mode:

```bash
mvn clean test -Pmobile
```

Run all non-manual tests:

```bash
mvn clean test -Pall
```

Run a specific Cucumber tag:

```bash
mvn clean test -Dcucumber.filter.tags="@web"
```

---

## Web UI Sample - Playwright

The Web sample uses Playwright Java and opens the configured page from:

```properties
web.base.url=https://example.com
web.browser=chromium
web.headless=true
```

Run it with:

```bash
mvn clean test -Pweb
```

If Playwright browsers are not installed on the machine, install them with:

```bash
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
```

Supported browser config values:

```text
chromium
firefox
webkit
```

---

## Mobile Sample - Appium / Mock / Sauce Labs Ready

The Mobile sample is runnable by default using mock mode:

```properties
mobile.execution.mode=mock
```

Run it with:

```bash
mvn clean test -Pmobile
```

Execution modes:

```text
mock  - Runs without a real device or Appium server
local - Placeholder for local Appium server execution
sauce - Placeholder for Sauce Labs mobile execution
```

Local Appium placeholders:

```properties
mobile.execution.mode=local
mobile.server.url=
mobile.platform=android
mobile.device.name=
mobile.app.path=
mobile.app.package=
mobile.app.activity=
```

Sauce Labs placeholders:

```properties
mobile.execution.mode=sauce
cloud.provider=sauce
sauce.username=
sauce.access.key=
sauce.region=us-west-1
sauce.app.url=
sauce.build.name=TestMate-AI-Phase1
sauce.platform.name=Android
sauce.platform.version=
sauce.device.name=Android GoogleAPI Emulator
```

The local and Sauce driver factories are intentionally placeholder-based until real device/app details are available.

---

## Parallel Execution

The Cucumber TestNG runner overrides the scenario DataProvider with parallel execution enabled.

```text
AI/API tests: designed for parallel execution
Web UI tests: uses thread-local Playwright session management
Mobile tests: uses thread-local mobile session management
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
- Run Web UI tests with Playwright sample
- Run Mobile tests in default mock mode
- Provide placeholders for local Appium and Sauce Labs execution
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
