# 4.3 Cross-Cutting Concerns Examples

This folder contains two projects to contrast the "Before Refactoring" (static logger) and the "After Refactoring" (injected `ILogger`).

* `/Before`: A console app showing `OrderService` tightly coupled to a static logger.
* `/After`: A console app showing `OrderService` depending on an `ILogger` interface.

## How to Run
Navigate to either `Before` or `After` and run:
```bash
dotnet run