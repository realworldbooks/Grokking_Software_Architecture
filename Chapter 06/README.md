# Chapter 6: Reliable API Design

Welcome to the companion code for Chapter 6. In Chapter 5, we explored Hexagonal Architecture to isolate our core domain; now, we shift our focus to how that domain communicates with the outside world. This chapter explores the evolution of data fetching and introduces the concept of treating APIs as strict parameters for Artificial Intelligence.

These examples demonstrate the journey from traditional REST endpoints to precision GraphQL queries, culminating in a modern AI-Ready architecture where Swagger and OpenAPI specifications control LLM agent behavior.

## Architectural Concepts Covered
### 1. The Over-Fetching Problem (REST):
- Traditional endpoints often return rigid, bulky JSON payloads.
- Clients are forced to download data they do not need, wasting bandwidth and processing power.

### 2. Precision Data Fetching (GraphQL):
- Allows the client to dictate the exact shape and size of the response.
- Solves over-fetching by using a single endpoint and a specific query language.

### 3. The AI-Ready API (Swagger/OpenAPI):
- Transforming API documentation into a Living Prompt for LLMs.
- Using schemas, descriptions, and examples to strictly dictate how an AI agent interacts with your system.

### 4. Server-Side Determinism:
- Forcing the AI to rely on the API for business logic (like shipping calculations) rather than hallucinating math.
- The AI acts as a conversational interface, while the API remains the authoritative source of truth.

## How to Run the Examples
1. **.NET (C#)**
  - Prerequisites: .NET 6.0 SDK or higher.
  - Navigate to the Chapter06/C#/ directory.
  - Run the interactive console menu:

```bash
dotnet run
```

2. **Java**
  - Prerequisites: Java 17 and Maven.
  - Navigate to the Chapter06/Java/ directory.
  - Compile and execute the interactive menu:
```bash
mvn clean compile exec:java
```

3. **Node.js (JavaScript)**
  - Prerequisites: Node.js (v18+).
  - Navigate to the Chapter06/JS/ directory.
  - Run the interactive menu:
```bash
node menu.js
```

4. **Python**
  - Prerequisites: Python 3.10+.
  - Navigate to the Chapter06/Python/ directory.
  - Run the interactive menu:
```bash
python menu.py
```

## Project Structure
The folders are organized by section, highlighting the progression of API design.

```PlainText
├── section_6_3_1_rest/                             # Basic REST Implementation
│   ├── FakeRestHandler                             # Simulates over-fetching bulky JSON
├── section_6_3_2_graphql/                          # Precision Data Fetching
│   ├── FakeGraphQLHandler                          # Simulates targeted payload queries
└── section_6_4_3_the_modern_architect_ai_rule/     # The Modern Architect (AI Rule)
    ├── Controllers/                                # Swagger-annotated routing
    ├── Domain/                                     # Core business entities isolated from API concerns
    ├── Infrastructure/                             # Data access and repository implementations
    ├── Interfaces/                                 # Formal contracts enforcing Dependency Inversion
    ├── Models/                                     # AI-instructed DTOs and Schemas
    └── Services/                                   # Deterministic business logic
```

### Feature Comparison Map

| Section | Architectural Goal | The Problem (Before) | The Solution (After) |
| :--- | :--- | :--- | :--- |
| **6.3.1 (REST)** | Standardized Resources | **Over-fetching:** The client receives ingredients and calories when it only needs the price. | **N/A:** This section establishes the baseline problem. |
| **6.3.2 (GraphQL)** | Precision Fetching | **Wasted Bandwidth:** Multiple round trips or heavy payloads slow down mobile clients. | **Single Request:** The client asks for exactly what it needs, drastically reducing payload size. |
| **6.4.3 (AI APIs)** | Deterministic LLMs | **Hallucinations:** AI agents try to calculate shipping math and give users incorrect totals. | **Living Prompts:** Swagger/OpenAPI docs strictly command the AI to pass data to the server for exact calculation. |