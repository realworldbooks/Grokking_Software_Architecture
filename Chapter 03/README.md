
# Chapter 3: The Principles of Sound Design

Welcome to the companion code for Chapter 3. While Chapter 2 focused on individual quality attributes, Chapter 3 follows up by getting into the "connective tissue" of software architecture: how components interact and how logic is grouped.

These examples demonstrate the transition from Tight Coupling to Loose Coupling, Low Cohesion to High Cohesion, individual SOLID Principles, and finally the Order Processor Refactor for putting it all together.

## Architectural Concepts Covered
1. **Coupling and Cohesion:**
- We explore the fundamental tension in architecture: keeping related things together (Cohesion) while keeping unrelated things apart (Coupling).
- The "Chatty" Interface Problem: Identifying when a client is forced to know too much about the internal workflow of a service.
- Functional Cohesion: Moving away from "Utility" classes toward modules with a single, well-defined business purpose.

2. **SOLID Principles in Practice:**
Each principle is isolated into its own directory to show how specific refactorings improve the overall system architecture:
- SRP (Section 3.3.1): Single Responsibility Principle.
- OCP (Section 3.3.2): Open/Closed Principle.
- LSP (Section 3.3.3): Liskov Substitution Principle.
- ISP (Section 3.3.4): Interface Segregation Principle.update README
- DIP (Section 3.3.5): Dependency Inversion Principle.

3. **The Order Processor Refactor:**
This is our primary "Before and After" study.
- Before: A monolithic "God Method" that handles validation, database writes, payment processing, and emailing in one place.
- After: A clean Coordinator (Facade) that orchestrates specialized, injected services.

## How to Run the Examples

1. .NET (C#)
- Prerequisites: .NET 6.0 SDK+.
- Navigate to the Chapter03/CSharp/ directory.
- Run the project:
```Bash
dotnet run
```
2. Java
- Prerequisites: Java 17 and Maven.
- Navigate to the Chapter03/Java/ directory.
- Compile and execute:
```Bash
mvn clean compile exec:java
```
3. Node.js (JavaScript)
- Prerequisites: Node.js (v16+) and npm.
- Navigate to the Chapter03/Node/ directory.
- Install dependencies and start:
```Bash
npm install
npm start
```

4. Python
- Prerequisites: Python 3.10+.
- Navigate to the Chapter03/Python/ directory.
- Run the interactive menu:
```Bash
python menu.py
```
Note: Ensure you have __init__.py files in your subdirectories to support the dynamic import system.

## Project Structure
The folders are organized by section number, with the SOLID principles broken out individually for clarity. Each scenario contains a before/ (Problem) and after/ (Solution) implementation.

```Plaintext
├── section_3_2_coupling_exercise/           # Managing Chatty Interfaces
├── section_3_3_1_srp/              # Single Responsibility Principle
├── section_3_3_2_ocp/              # Open/Closed Principle
├── section_3_3_3_lsp/              # Liskov Substitution Principle
├── section_3_3_4_isp/              # Interface Segregation Principle
├── section_3_3_5_dip/              # Dependency Inversion Principle
└── section_3_4_order_processor/    # The Full Order Processor Refactor
```
## Feature Comparison Map

| Section | Architectural Goal | Key Principle | Solution (After) |
| :--- | :--- | :--- | :--- |
| **3.2** | **Reduce Coupling, Increase Cohesion** | Abstraction | **Facade Pattern**: Single entry point for workflow. |
| **3.3.1** | **Maintainability** | **SRP** | Separating data processing from reporting logic. |
| **3.3.2** | **Extensibility** | **OCP** | Adding new features without modifying existing code. |
| **3.3.3** | **Reliability** | **LSP** | Ensuring subclasses can stand in for their parents. |
| **3.3.4** | **Flexibility** | **ISP** | Splitting large interfaces into specific, small ones. |
| **3.3.5** | **Testability** | **DIP** | Orchestrating via injected contracts (Interfaces). |
| **3.5** | **System Design** | **SOLID** | Full Order Processor decoupled into discrete services. |