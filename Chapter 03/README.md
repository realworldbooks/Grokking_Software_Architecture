# Chapter 3: Coupling, Cohesion, and SOLID Principles

Welcome to the companion code for **Chapter 3**. 

In this chapter, we tackle the structural integrity of your code. We explore how tight coupling creates brittle systems, and how applying the SOLID principles acts as the ultimate antidote, allowing us to build flexible, "plug-and-play" architectures.

## Core Concepts Covered

This repository contains the code for the seven major architectural lessons taught in the book. Almost all examples include a `Before` (the anti-pattern) and an `After` (the refactored architecture) state for direct comparison:

1. **Section 3.1: Coupling Test** - Refactoring a "chatty" API into a "chunky" payload to reduce overhead and client complexity.
2. **Section 3.2: Single Responsibility Principle (SRP)** - Breaking up a bloated `Player` God Class into focused tactical, action, and persistence components.
3. **Section 3.3: Open/Closed Principle (OCP)** - Eliminating endless `if/else` statements by injecting new playbook strategies into a `Midfielder`.
4. **Section 3.4: Liskov Substitution Principle (LSP)** - Proving why a `Goalie` cannot safely substitute a generic field player contract.
5. **Section 3.5: Interface Segregation Principle (ISP)** - Curing the "Fat Interface" trap by separating field drills from goalie drills.
6. **Section 3.6: Dependency Inversion Principle (DIP)** - Forcing a `Coach` to depend on abstract `IPlayer` contracts rather than concrete player implementations.
7. **Section 3.7: Order Processor Refactor** - The grand finale: refactoring a monolithic script into a clean `OrderService` coordinator that delegates to injected, single-responsibility components.

## Choose Your Language

Every example has been translated into four major enterprise languages. Choose your preferred language folder below. **Each folder contains its own `README.md` with exact instructions on how to run the code.**

* 🟦 **[C# (.NET)](./csharp/)**
* ☕ **[Java](./java/)**
* 🐍 **[Python](./python/)**
* 🟨 **[JavaScript (Node.js)](./javascript/)**

**All examples in this chapter are 100% zero-setup.**