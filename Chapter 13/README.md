# Chapter 13: Architecting for the Delivery Lifecycle (CI/CD)

Welcome to the companion code for Chapter 13. This chapter explores how your architectural choices dictate your release pipeline. The examples demonstrate **Automated Guardrails** - codifying your structural boundaries as executable **Fitness Functions** that run automatically in your CI pipeline - plus the full **GitHub Actions Delivery Pipeline Blueprint** that turns raw code into a verified, immutable container artifact.

These examples demonstrate the **Architecture Governance** concept: instead of relying on human code reviewers to catch architectural violations, we move our governance out of people's heads and drop it right into our Continuous Delivery conveyor belt.

## Architectural Concepts Covered

1. **Listing 13.1: Architectural Boundary Enforcement (Section 13.3.2):**
   - **The Downward Dependency Rule:** The Domain Layer must NEVER depend on the Infrastructure Layer. This keeps the protected core of the system pure and isolated.
   - **The Naming Convention Rule:** All Controllers must follow a strict naming convention (*Controller suffix) and reside in the Presentation layer.
   - **The Fitness Function:** An automated mechanism that provides an objective integrity check of an architectural characteristic. Like a unit test ensures your business logic calculates correctly, a fitness function ensures your code structure adheres to your design blueprints.
   - **Guardrail Implementation per Language:**
     - **C#:** Uses **NetArchTest**, the industry-standard architecture testing library for .NET
     - **Java:** Uses **ArchUnit**, the industry-standard architecture testing library for Java
     - **Python:** Uses a **custom Fitness Function** engine that parses source files into an AST and evaluates them against the same architectural rules
     - **JavaScript:** Uses a **custom Fitness Function** engine that reads source files and evaluates them against the same architectural rules

2. **Listing 13.2: GitHub Actions Delivery Pipeline (Section 13.5):**
   - **The Conveyor Belt of Truth:** The declarative YAML blueprint that turns code into a verified, immutable artifact.
   - **The Governance Gate:** `dotnet test` runs both traditional unit tests AND the architectural fitness functions from Listing 13.1. A violation = build failure = deployment halted.
   - **Immutable Cattle Runners:** The pipeline uses ephemeral, containerized build runners (Pets vs. Cattle from Chapter 9).
   - **Double-Tagged Immutable Artifacts:** Containers are tagged with both `latest` and the exact git commit SHA for traceability.
   - **Perimeter Security Isolation:** Credentials are injected from the platform's protected repository vault engine (secrets), never hardcoded.
   - **Language Mapping:** The YAML includes a comment block showing how the Governance Gate command swaps per language (`dotnet test` → `mvn test` → `pytest` → `npm test`) while the structural stage-gate logic stays identical.

## How to Run the Examples

### 1. .NET (C#)
- **Prerequisites:** .NET 8.0 SDK or higher.
- **Tools Used:**
  - **NetArchTest.Rules:** The architecture testing library for .NET that parses compiled assemblies into an internal dependency graph.
  - **Microsoft.EntityFrameworkCore:** Used by the sample Order Service infrastructure layer.
- Navigate to the `Chapter13/C#/` directory.
- Run the interactive console master menu:
  ```bash
  dotnet run
  ```

### 2. Java
- **Prerequisites:** Java 17 and Maven.
- **Tools Used:**
  - **ArchUnit:** The industry-standard architecture testing library for Java.
  - **Gson:** A JSON library used for parsing the Examples.json configuration.
- Navigate to the `Chapter13/Java/` directory.
- Compile and execute the interactive master menu:
  ```bash
  mvn clean compile exec:java
  ```

### 3. Node.js (JavaScript)
- **Prerequisites:** Node.js (v18+).
- Navigate to the `Chapter13/JS/` directory.
- Start the menu:
  ```bash
  node menu.js
  ```

### 4. Python
- **Prerequisites:** Python 3.12+.
- **Tools Used:** No external dependencies. The fitness function uses only the Python standard library (`ast`, `pathlib`).
- Navigate to the `Chapter13/Python/` directory.
- Run the menu:
  ```bash
  python menu.py
  ```

## Project Structure
All languages share a unified folder structure. Each language contains the **Shop-Zilla Order Service** sample project (Domain, Infrastructure, and Presentation layers) plus the **Demo** that runs the Architectural Fitness Functions against it.

```text
├── [Root Menu File]                          # The Master CLI Menu (Program.cs, menu.py, menu.js, Main.java)
│
└── section_13_3_2_architecture_governance/   # Listing 13.1: Architectural Boundary Enforcement
    ├── Demo                                  # Runs the Fitness Functions against the sample project
    ├── domain/                               # The protected core (Order, OrderStatus)
    ├── infrastructure/                       # Data access layer (OrderRepository/DbContext)
    └── presentation/                         # Controllers (BaseController, OrderController)
```

Additionally, the **GitHub Actions Delivery Pipeline** (Listing 13.2) lives at the Chapter 13 root as a fully-commented YAML blueprint:
```text
├── Listing 13.2 - GitHub Actions Delivery Pipeline.yml
```

## Feature Comparison Map
| Section | Architectural Goal | The Problem (Before) | The Solution (After) |
| :--- | :--- | :--- | :--- |
| **13.3.2** | **Guardrails as Code** | **Manual Code Review:** Human reviewers get tired, miss boundary violations, and can't keep up with AI-generated code velocity. | **Fitness Functions:** NetArchTest (C#), ArchUnit (Java), or custom AST parsers (Python/JS) automatically fail the build when a boundary is crossed. |
| **13.5** | **Automated Delivery** | **ClickOps Gauntlet:** Manual deployments with 40-step checklists executed by fatigued engineers at 2 AM. | **GitHub Actions Blueprint:** A declarative conveyor belt that runs tests + fitness functions, bakes an immutable container, and pushes it with a traceable SHA tag. |

## The "Accidental Arsonist" Scenario
Every Demo in this chapter includes the **Accidental Arsonist Simulation** - what happens when a developer (or AI assistant) accidentally breaks an architectural boundary. In a real CI pipeline, the fitness function catches the violation instantly, the build flashes red, and the deployment halts before the code can reach a production server. The demos demonstrate this by running the fitness functions against a clean codebase and explaining what would happen if a violation were present.