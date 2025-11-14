# 3.4 In Action: "Before vs. After" SRP Refactor

This folder contains the two runnable console apps demonstrating the refactoring of the monolithic `OrderProcessor` class.

* `/BadWay`: The monolithic `OrderProcessor` class that violates SRP.
* `/GoodWay`: The refactored solution, where logic has been separated into smaller, focused classes (`OrderValidator`, `PaymentService`, etc.) and a coordinator (`OrderService`).

## How to Run

Navigate to either `BadWay` or `GoodWay` and run:
```bash
dotnet run
