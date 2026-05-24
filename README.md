# TestMate AI - Spring Boot Powered AI/API/UI/Mobile Automation Platform

TestMate AI is an enterprise-style Java automation platform for validating AI-powered applications, LLM/chatbot responses, API services, web UI flows, and mobile app flows.

The project is being built in two phases:

1. Spring Boot powered AI/API/UI/Mobile automation framework
2. Spring Boot backend + React dashboard + test execution engine

The repository now contains the Phase 1 automation framework foundation and the initial Phase 2 backend/dashboard scaffold.

---

## Current Status

Phase 1 foundation:

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

Phase 2 scaffold:

- Spring Boot application entry point
- REST endpoints for framework metadata and health
- REST endpoints for creating and viewing test execution requests
- In-memory execution tracking service
- React dashboard scaffold using Vite
- Dashboard form for suite/tag/environment/mode selection
- Dashboard execution history table

GitHub Actions is intentionally manual-only for now.

RAG testing is intentionally not implemented in this version.

---

## Tech Stack

- Java 17
- Spring Boot Web + Actuator
- Spring Boot test context
- Maven
- Cucumber BDD
- TestNG
- RestAssured
- Playwright Java
- Appium Java Client
- React + Vite
- Axios
- Jackson
- Lombok
- SLF4J / Logback
- GitHub Actions

---

## Enterprise Project Structure

```text
src/main/java/com/testmate/ai
├── TestMateAiApplication.java
└── platform
    ├── controller      # REST controllers
    ├── model           # API request/response DTOs
    └── service         # Test execution service layer

src/test/java/com/testmate/ai
├── core
│   └── config          # Spring/Cucumber config, framework config, config reader
├── clients             # AI client contract and provider skeletons
├── context             # Thread-local scenario context
├── hooks               # Cucumber lifecycle hooks
├── mobile
│   ├── screens         # Screen Object Model classes
│   ├── ScreenObjectManager
│   ├── MobileSessionManager
│   ├── MockMobileDriver
│   ├── LocalMobileDriverFactory
│   └── SauceLabsMobileDriverFactory
├── models              # AI request and response models
├── reporting           # Custom report generation
├── runners             # TestNG Cucumber runner with parallel execution
├── stepdefs            # Cucumber step definitions
├── validators          # AI response validators
└── web
    ├── pages           # Page Object Model classes
    ├── PageObjectManager
    ├── WebSessionManager
    └── WebActions

src/test/resources
├── config              # Default framework properties
├── features            # Cucumber feature files for AI, Web, and Mobile samples
└── testdata            # Sample prompt test data

frontend
├── index.html
├── package.json
├── .env.example
└── src
    ├── App.jsx
    └── styles.css
```

---

## Backend API Endpoints

Start backend:

```bash
mvn spring-boot:run
```

Health:

```bash
GET http://localhost:8080/api/framework/health
```

Framework metadata:

```bash
GET http://localhost:8080/api/framework/metadata
```

Start execution:

```bash
POST http://localhost:8080/api/executions
```

Sample request:

```json
{
  "suite": "ai",
  "tags": "@ai-validation",
  "environment": "local",
  "executionMode": "mock"
}
```

Get all executions:

```bash
GET http://localhost:8080/api/executions
```

Get execution by id:

```bash
GET http://localhost:8080/api/executions/{executionId}
```

---

## React Dashboard

Start dashboard:

```bash
cd frontend
npm install
npm run dev
```

Frontend environment example:

```text
VITE_API_BASE_URL=http://localhost:8080
```

The current dashboard can:

- Load framework metadata
- Create execution requests
- View in-memory execution history
- Select suite, tags, environment, and execution mode

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

Install Playwright browsers if needed:

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

## Next Phase 2 Work

- Connect REST execution service to the real Maven/Cucumber execution engine
- Persist execution history in a database
- Add report download APIs
- Add live execution log streaming
- Add authentication and role-based access
- Add dashboard report viewer
- Add environment and device management screens

---

## Author

Avinash Kandukuri

Senior SDET | Java Automation Engineer | AI Testing & Automation Framework Specialist
