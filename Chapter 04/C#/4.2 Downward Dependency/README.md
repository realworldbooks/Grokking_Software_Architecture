# 4.2 Downward Dependency Examples

This folder contains two projects to contrast the "Before Refactoring" (violating the rule) and the "After Refactoring" (following the rule).

* `/Before`: A console app simulating a data layer that *incorrectly* calls an upward layer.
* `/After`: A console app showing the correct pattern, where the business layer calls the data layer via an interface.

## How to Run
Navigate to either `Before` or `After` and run:
```bash
dotnet run