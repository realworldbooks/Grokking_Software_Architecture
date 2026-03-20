# Chapter 2: The Architect's Decision Toolkit

Welcome to the companion code for **Chapter 2**. 

This chapter marks the transition from thinking like a programmer (writing code that "just works") to thinking like a Clarity Engineer (designing systems that are maintainable, testable, and scalable). 

The examples in this chapter move away from abstract theory and provide concrete, runnable proof of how architectural decisions impact your codebase. 

## Core Concepts Covered

This repository contains the code for the five major architectural lessons taught in the book:

1. **Maintainability (Section 2.3.2):** Curing the "all-in-one" function by applying the Separation of Concerns principle to isolate data, logic, and execution.
2. **Testability (Section 2.3.3):** Using Dependency Injection to decouple business logic from a live database, turning a brittle system into an instantly testable one.
3. **Performance (Section 2.3.4):** Implementing a "Smart Cache" architecture to demonstrate the tradeoff between brute-force execution and architectural complexity.
4. **Constraints in Action (Section 2.4.1):** Writing a pragmatic, "good enough for now" inline CSV exporter when faced with tight deadlines and web framework constraints.
5. **The Weighted Decision Model (Section 2.7.1):** A mathematical, matrix-driven tool that proves *why* you chose a specific technology, replacing guesswork with defensible architecture.

## Choose Your Language

To make this as accessible as possible, every single example has been translated into four major enterprise languages with idiomatic formatting and structures. 

Choose your preferred language folder below. **Each folder contains its own `README.md` with exact instructions on how to run the code.**

* 🟦 **[C# (.NET)](./csharp/)**
* ☕ **[Java](./java/)**
* 🐍 **[Python](./python/)**
* 🟨 **[JavaScript (Node.js)](./javascript/)**

## The "Zero-Setup" Philosophy

As a Clarity Engineer, your time is valuable. You shouldn't have to spend an hour configuring Docker containers, installing testing frameworks, or running `npm install` just to learn an architectural concept. 

**Every example in this chapter is 100% zero-setup.** We have built lightweight, in-memory simulations of databases, web requests, and unit test assertions directly into the code. You can clone this repository, open your language of choice, and hit "Run" immediately.