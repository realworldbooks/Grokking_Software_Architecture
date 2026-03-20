# Chapter 2: The Architect's Decision Toolkit (Java)

Welcome to the Java companion code for **Chapter 2**. 

In this chapter, we transition from writing "scripts" to designing "systems." These examples demonstrate how a Clarity Engineer applies core architectural principles to make code more maintainable, testable, and performant.

## What's Inside

This is a standard Java project containing the following examples mapped directly to the book sections:

1. **Section 2.3.2: Maintainability (`/2.3.2 Maintainability`)** - Refactoring a tightly coupled shopping cart into isolated data, logic, and execution layers.
2. **Section 2.3.3: Testability (`/2.3.3 Testability`)** - Using Dependency Injection (DI) to decouple a report generator from a live database, making it instantly testable.
3. **Section 2.3.4: Performance (`/2.3.4 Performance`)** - Implementing the "Smart Cache" architecture to bypass expensive, brute-force database queries.
4. **Section 2.4.1: Constraints in Action (`/2.4.1 Constraints In Action`)** - A pragmatic, "good enough for now" inline CSV exporter simulating a web endpoint constraint.
5. **Section 2.7.1: Weighted Decision Model (`/2.7.1 Weighted Decision Model`)** - A mathematical, matrix-driven approach to choosing the right technology stack without relying on guesswork.

## How to Run the Code

This project is designed to be **100% zero-setup**. There are no external dependencies or databases required. Everything runs perfectly using standard Java 17+.

**Using an IDE (IntelliJ, Eclipse, VS Code):**
1. Open the `Chapter 02/java/` folder in your IDE.
2. Navigate to the specific section folder you want to run (e.g., `2.3.2 Maintainability`).
3. Open the `Main.java` file inside that folder.
4. Right-click the file and select **Run 'Main.main()'**.
5. Watch the console output demonstrate the architectural lesson!