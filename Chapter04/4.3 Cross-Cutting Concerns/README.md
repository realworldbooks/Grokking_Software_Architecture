# 4.3 Cross-Cutting Concerns Examples

This folder contains two projects to contrast the "Bad Way" (static logger) and the "Good Way" (injected `ILogger`).

* `/BadWay`: A console app showing `OrderService` tightly coupled to a static logger.
* `/GoodWay`: A console app showing `OrderService` depending on an `ILogger` interface.

## How to Run
Navigate to either `BadWay` or `GoodWay` and run:
```bash
dotnet run