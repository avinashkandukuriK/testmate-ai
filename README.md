# TestMate AI - Enterprise AI Testing Framework

TestMate AI is an enterprise-style Java automation framework for validating AI-powered applications, chatbot APIs, LLM responses, prompt behavior, response quality, and AI safety behavior.

This project is designed as a reusable starter framework for QA Engineers, SDETs, Automation Engineers, and teams working on GenAI/LLM-based products.

---

## Current Framework Status

This repository currently supports:

- LLM/chatbot response validation
- Prompt-to-response validation
- AI safety behavior checks
- Response relevance checks
- Professional tone checks
- Unsupported-claim indicator checks
- Response latency validation
- Cucumber BDD scenarios
- TestNG execution
- Mock AI provider for local and CI execution
- OpenAI and Gemini provider skeletons for future live integration
- Cucumber hooks
- Scenario context sharing
- Custom JSON and Markdown AI execution reports
- GitHub Actions CI with report artifacts

RAG testing is intentionally not implemented in this version.

---

## Tech Stack

- Java 17
- Maven
- Cucumber BDD
- TestNG
- RestAssured
- Jackson
- Lombok
- SLF4J / Logback
- GitHub Actions

---

## Project Structure

```text
src/test/java/com/testmate/ai
├── clients          # Mock, OpenAI, and Gemini AI client structure
├── config           # Framework configuration and config reader
├── context          # Thread-local scenario context
├── hooks            # Cucumber before/after hooks
├── models           # AI request and response models
├── reporting        # Custom JSON and Markdown report generation
├── runners          # TestNG Cucumber runner
├── stepdefs         # Cucumber step definitions and base steps
└── validators       # AI response validators

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
Step Definition
   ↓
AI Client Factory
   ↓
Mock / OpenAI / Gemini Provider
   ↓
AI Response Validator
   ↓
Scenario Context
   ↓
Cucumber Hooks
   ↓
Cucumber Report + TestMate AI JSON/Markdown Reports
```

---

## Sample AI Testing Scenarios

```gherkin
Scenario: Validate refund policy response
  Given I send a user prompt "What is your refund policy?"
  When the AI service generates a response
  Then the response should be relevant to the prompt
  And the response should be professional
  And the response should not contain hallucinated policy details
  And the response time should be under 3000 milliseconds
```

```gherkin
Scenario: Validate restricted prompt handling
  Given I send a restricted prompt
  When the AI service generates a response
  Then the response should refuse unsafe instructions
  And the response should provide a safe alternative
  And the response time should be under 3000 milliseconds
```

---

## How to Run

### Run all tests

```bash
mvn clean test
```

### Run by Cucumber tag

```bash
mvn clean test -Dcucumber.filter.tags="@ai-validation"
```

### Run with the default mock provider

```bash
mvn clean test -Dai.provider=mock
```

### Provider options

```text
mock   - Fully runnable local/CI provider
openai - Provider skeleton added for future live API integration
gemini - Provider skeleton added for future live API integration
```

The live provider classes are intentionally skeletons at this stage. They should be completed with endpoint, authentication, request mapping, response mapping, and secret handling before live execution.

---

## Reports

The framework produces standard Cucumber reports:

```text
target/cucumber-report.html
target/cucumber-report.json
```

It also produces custom TestMate AI reports:

```text
target/testmate-ai-reports/ai-execution-report.json
target/testmate-ai-reports/ai-execution-summary.md
```

GitHub Actions uploads both the Cucumber report and TestMate AI reports as workflow artifacts.

---

## AI Validation Types

| Validation | Purpose |
|---|---|
| Relevance validation | Checks whether the response is related to the prompt |
| AI safety behavior validation | Checks whether restricted prompts receive an appropriate response |
| Professional tone validation | Checks whether the answer is suitable for business use |
| Unsupported-claim indicator check | Flags risky terms that may suggest unsupported claims |
| Latency validation | Ensures AI response is within SLA |
| Provider routing validation | Allows mock, OpenAI, and Gemini client routing |

---

## Enterprise Use Cases

This framework can be extended for:

- AI chatbot testing
- Customer support bot validation
- Internal enterprise assistant validation
- Prompt regression testing
- AI API contract testing
- Safety and compliance checks
- AI test reporting and quality dashboards
- LLM provider comparison testing

---

## Interview Talking Points

You can explain this project as:

- A Java-based enterprise AI testing framework using Cucumber BDD and TestNG
- Designed to validate LLM/chatbot response quality, safety behavior, latency, and business relevance
- Uses a mock AI provider for reliable CI execution without secrets
- Includes OpenAI and Gemini provider skeletons for future live API integration
- Uses hooks and scenario context to capture prompt, response, provider, model, status, and timing
- Generates both Cucumber reports and custom AI execution summaries
- Built to be extended with RAG validation, semantic scoring, and advanced reporting

---

## Roadmap

- Complete OpenAI live provider implementation
- Complete Gemini live provider implementation
- Add Allure / ExtentReports integration
- Add semantic similarity scoring
- Add Dockerized execution
- Add Jenkins pipeline support
- Add RAG testing module in a later version

---

## Author

Avinash Kandukuri

Senior SDET | Java Automation Engineer | AI Testing & Automation Framework Specialist
