# Chapter 2: The Architect's Decision Toolkit (C#)

Welcome to the C# companion code for **Chapter 2**. 

In this chapter, we transition from writing "scripts" to designing "systems." These examples demonstrate how a Clarity Engineer applies core architectural principles to make code more maintainable, testable, and performant.

## What's Inside

This project is a single C# Console Application containing the following examples:

1. **Section 2.3.2: Maintainability (`/2.3.2 Maintainability`)** - Refactoring a tightly coupled shopping cart into isolated data, logic, and execution layers.
2. **Section 2.3.3: Testability (`/2.3.3 Testability`)** - Using Dependency Injection (DI) to decouple a report generator from a live database, making it instantly testable.
3. **Section 2.3.4: Performance (`/2.3.4 Performance`)** - Implementing the "Smart Cache" architecture to bypass expensive, brute-force database queries.
4. **Section 2.4.1: Constraints in Action (`/2.4.1 Constraints In Action`)** - A pragmatic, "good enough for now" inline CSV exporter simulating a web endpoint constraint.
5. **Section 2.7.1: Weighted Decision Model (`/2.7.1 Weighted Decision Model`)** - A mathematical, matrix-driven approach to choosing the right technology stack without relying on guesswork.

## How to Run the Code

This project is designed to be **100% zero-setup**. There are no external NuGet packages or databases required.

### Option 1: Using an IDE (Recommended)
1. Open the `Chapter02/C#/` folder in Visual Studio or VS Code.
2. Open the `Program.cs` file in the root directory.
3. Uncomment the specific example you want to run (e.g., `Example1.ShoppingCartDemo.Run();`).
4. Hit the **Play/Run** button!

### Option 2: Using the CLI
If you have the .NET SDK installed, simply navigate to this directory in your terminal and run:

```bash
cd Chapter02/C#/
dotnet run
