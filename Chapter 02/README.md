# Chapter 2: The Architect's Decision Toolkit
Welcome to the companion code for Chapter 2 of Grokking Software Architecture. In this chapter, we transition from writing "scripts" to designing "systems." These examples demonstrate how a Clarity Engineer applies core architectural principles to make code more maintainable, testable, and performant across C#, Java, Node.js, and Python.

 ## Architectural Concepts Covered
 
 - **Section 2.3.2 Maintainability:** Refactoring a tightly coupled shopping cart into isolated data and logic layers to eliminate "Magic Numbers" and "God Methods."
 - **Section 2.3.3 Testability:** Using Dependency Injection (DI) to decouple business logic from infrastructure, enabling isolated unit testing via Fakes and Mocks.
 - **Section 2.3.4 Performance:** Implementing the Cache-Aside pattern to protect expensive data sources and reduce user latency.
 - **Section 2.4.1 Constraints in Action:** Orchestrating requests through layers while adhering to technical (HTTP/CSV) and business (validation) constraints.
 - **Section 2.7.1 Weighted Decision Model:** A quantitative, matrix-driven approach to choosing technology stacks based on project-specific priorities.

## How to Run the Examples

1. .NET (C#) 
- Prerequisites: .NET 6.0 SDK or later.
- Navigate to Chapter02/C#/
- Run: 
```bash 
dotnet run
``` 
The menu uses Reflection to dynamically execute the chosen class.

2. Java
- Prerequisites: Java 17 and Maven.
- Navigate to Chapter02/Java/
- Run: 
```bash
mvn clean compile exec:java
```
Note: Uses the Jackson library to parse the examples.json manifest.

3. Node.js (JavaScript)
- Prerequisites: Node.js (v16+) and npm.
- Navigate to Chapter02/Node/
- Run:  
```bash 
npm install
npm start
``` 
Note: Uses dynamic require() to load modules at runtime.

4. Python
- Prerequisites: Python 3.10+.
- Navigate to Chapter02/Python/
- Run:  
```bash
python menu.py
```
Note: Requires __init__.py files in subdirectories for dynamic module importing.

## Project Structure

Each language follows this standardized folder hierarchy. For sections involving refactoring, you will find both a before/ (unoptimized) and after/ (architecturally sound) implementation.

```Plaintext
├── section_2_3_2_maintainability/  # Shopping Cart (Before/After)
├── section_2_3_3_testability/      # Dependency Injection (Before/After)
├── section_2_3_4_performance/      # Cache-Aside Pattern (Before/After)
├── section_2_4_1_constraints/      # Layered Architecture & HTTP
└── section_2_7_1_decision_model/   # Weighted Decision Matrix
```

## Feature Comparison 

| Section | Architectural Goal | Key Pattern | Implementation Detail |
| :--- | :--- | :--- | :--- |
| **2.3.2** | **Maintainability** | Separation of Concerns | Constants & Method Decomposition |
| **2.3.3** | **Testability** | Inversion of Control | Constructor Injection & Test Doubles |
| **2.3.4** | **Performance** | Cache-Aside | In-Memory Key/Value Store |
| **2.4.1** | **Constraints** | Layered Orchestration | Controller -> Service -> DB Flow |
| **2.7.1** | **Decision Making** | Quantitative Analysis | Weighted Scoring Algorithm |