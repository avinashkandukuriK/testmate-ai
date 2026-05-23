# TestMate AI - Enterprise AI Testing Framework

TestMate AI is an enterprise-style Java test automation framework for validating AI-powered applications, chatbot APIs, LLM responses, prompt behavior, safety rules, and response quality.

This project is designed as a reusable starter framework for QA Engineers, SDETs, Automation Engineers, and teams working on GenAI/LLM-based products.

---

## Tech Stack

- Java 17
- Maven
- Cucumber BDD
- TestNG
- RestAssured
- Jackson
- JSON Schema Validation
- Lombok
- SLF4J / Logback
- GitHub Actions

---

## What This Framework Tests

- AI chatbot responses
- Prompt-to-response behavior
- Response relevance
- Response safety
- Response consistency
- Hallucination risk indicators
- API response structure
- Response latency
- Business-rule validation
- AI-generated summaries

---

## Key Features

- Enterprise-style layered architecture
- Cucumber BDD feature files
- TestNG runner support
- Mock AI provider for local and CI execution
- OpenAI-compatible client structure for future live API testing
- Reusable AI response validators
- Scenario context for sharing test data
- Environment-based configuration
- JSON test data support
- GitHub Actions CI workflow
- Clean structure for easy extension

---

## Project Structure

```text
src/test/java/com/testmate/ai
├── clients          # AI service clients
├── config           # Framework configuration
├── context          # Scenario context
├── hooks            # Cucumber hooks
├── models           # Request/response models
├── runners          # TestNG Cucumber runners
├── stepdefs         # Step definitions
├── utils            # Utility classes
└── validators       # AI response validators

src/test/resources
├── config           # Environment configuration
├── features         # Cucumber feature files
├── schemas          # JSON schemas
└── testdata         # Test data files
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

### Run with a specific AI provider

By default, the framework uses a mock AI provider so tests can run without secrets.

```bash
mvn clean test -Dai.provider=mock
```

Future live API usage can be configured using environment variables:

```bash
export AI_BASE_URL=https://api.example.com/v1/chat/completions
export AI_API_KEY=your_api_key
export AI_MODEL=gpt-example
```

---

## AI Validation Types

| Validation | Purpose |
|---|---|
| Relevance validation | Checks whether the response is related to the prompt |
| Safety validation | Checks whether unsafe prompts are refused safely |
| Professional tone validation | Checks whether the answer is suitable for business use |
| Hallucination indicator check | Flags risky terms that may suggest unsupported claims |
| Latency validation | Ensures AI response is within SLA |
| Schema validation | Ensures API response follows expected contract |

---

## Enterprise Use Cases

This framework can be extended for:

- AI chatbot testing
- Customer support bot validation
- Internal enterprise assistant validation
- Prompt regression testing
- RAG response validation
- AI API contract testing
- Safety and compliance checks
- AI test reporting and quality dashboards

---

## Roadmap

- Add Allure / ExtentReports integration
- Add live OpenAI-compatible provider execution
- Add RAG source citation validation
- Add semantic similarity scoring
- Add test result trend dashboard
- Add Dockerized execution
- Add Jenkins pipeline support

---

## Author

Avinash Kandukuri

Senior SDET | Java Automation Engineer | AI Testing & Automation Framework Specialist
