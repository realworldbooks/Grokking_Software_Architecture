# Chapter 02: Architectural Fundamentals in Action

This repository contains the interactive code examples for Chapter 02. These examples are designed to demonstrate the "Before" (unoptimized) and "After" (architecturally sound) states of common software patterns, focusing on Maintainability, Testability, and Performance.

## Architectural Concepts Covered
- **Maintainability:** Eliminating "Magic Numbers" and "God Methods" through Separation of Concerns.

- **Testability:** Implementing Dependency Injection (DI) to decouple business logic from infrastructure.

- **Performance:** Utilizing the Cache-Aside pattern to protect data sources and reduce latency.

- **Constraints:** Managing technical and business constraints within a Layered Architecture.

- **Decision Making:** Applying a Weighted Decision Model for objective technology selection.

## How to Run the Examples
**1. .NET (C#)**
Prerequisites: .NET 6.0 SDK or later.

Navigate to the Chapter02/CSharp directory.

Run the application using the dotnet CLI:

```Bash
dotnet run
```

**2. Java**
Prerequisites: Java 17 and Maven.

Navigate to the Chapter02/Java directory.

Compile and execute the interactive menu:

```Bash
mvn clean compile exec:java
Note: This project uses the Jackson library for dynamic JSON parsing of the example manifest.
```

**3. Node.js (JavaScript)**
Prerequisites: Node.js (v16+) and npm.

Navigate to the Chapter02/Node directory.

Install the menu dependencies:

```Bash
npm install
```
Launch the interactive menu:

```Bash
npm start
```
## Project Structure
The project is organized by architectural quality attribute to allow for easy comparison:

```Plaintext
├── section_2_3_2_maintainability/  # Shopping Cart refactoring
├── section_2_3_3_testability/    # Dependency Injection & Mocking
├── section_2_3_4_performance/    # Cache-Aside pattern implementation
├── section_2_4_constraints/      # Layered architecture & HTTP constraints
└── section_2_5_decisionmodel/    # Quantitative Decision Matrix
```
## Using the Interactive Menu
Each implementation features a dynamic menu driven by an examples.json file.

Select a numeric option to trigger a specific architectural scenario.

Observe the console output to see architectural notes and execution logic.

Compare the before and after folders in the source code to see the refactoring in detail.