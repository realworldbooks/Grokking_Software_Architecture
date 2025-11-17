# 4.2 Downward Dependency Examples

This folder contains two projects to contrast the "Bad Way" (violating the rule) and the "Good Way" (following the rule).

* `/BadWay`: A console app simulating a data layer that *incorrectly* calls an upward layer.
* `/GoodWay`: A console app showing the correct pattern, where the business layer calls the data layer via an interface.

## How to Run
Navigate to either `BadWay` or `GoodWay` and run:
```bash
dotnet run