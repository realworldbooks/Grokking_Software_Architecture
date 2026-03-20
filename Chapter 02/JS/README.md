# Chapter 2: The Architect's Decision Toolkit (JavaScript)

Welcome to the JavaScript/Node.js companion code for **Chapter 2**. 

In this chapter, we transition from writing "scripts" to designing "systems." These examples demonstrate how a Clarity Engineer applies core architectural principles to make code more maintainable, testable, and performant.

## What's Inside

This directory contains standalone examples mapped directly to the book sections, broken down into their own folders to enforce proper Separation of Concerns:

1. **Section 2.3.2: Maintainability (`/2.3.2 Maintainability`)** - Refactoring a tightly coupled shopping cart into isolated data, logic, and execution layers.
2. **Section 2.3.3: Testability (`/2.3.3 Testability`)** - Using Dependency Injection (DI) to decouple a report generator from a live database, making it instantly testable.
3. **Section 2.3.4: Performance (`/2.3.4 Performance`)** - Implementing the "Smart Cache" architecture to bypass expensive, brute-force database queries.
4. **Section 2.4.1: Constraints in Action (`/2.4.1 Constraints In Action`)** - A pragmatic, "good enough for now" inline CSV exporter simulating an Express.js web endpoint constraint.
5. **Section 2.7.1: Weighted Decision Model (`/2.7.1 Weighted Decision Model`)** - A mathematical, matrix-driven approach to choosing the right technology stack without relying on guesswork.

## How to Run the Code

This project is designed to be **100% zero-setup**. There is no `package.json`, and no `npm install` is required. Everything runs perfectly using vanilla Node.js.

To run an example, simply navigate to its folder in your terminal (using quotes for the folder names) and execute the `index.js` file:

```bash
# Example: Running the Performance / Caching lesson
cd "2.3.4 Performance"
node index.js

Alternatively, you can open the entire javascript folder in an IDE like VS Code, open the index.js file for the section you are currently reading, and run it directly from your integrated terminal!