# Chapter 3 Code Examples

This folder contains all the runnable C# code samples for Chapter 3: The Principles of Sound Structure.

Each example is a self-contained .NET 8 Console Application.

## How to Run

1.  Make sure you have the .NET 8 SDK installed.
2.  Navigate into a specific example's directory (e.g., `cd 3.3-SRP/GoodWay`).
3.  Run the application: `dotnet run`

## Examples

* **3.2-Coupling-Exercise:** The `UserReportGenerator` example for the coupling exercise.
* **3.3-SRP:** "Bad Way" (monolithic `Player`) and "Good Way" (separated `Player`, `TacticsEngine`, `PlayerRepository`).
* **3.3-OCP:** "Bad Way" (if/else `ExecutePlay`) and "Good Way" (using the `IPlay` interface).
* **3.3-LSP:** "Bad Way" (deceptive `Goalie` class) and "Good Way" (correct `Midfielder` substitute).
* **3.3-ISP:** "Bad Way" ("fat" `ITrainingSession`) and "Good Way" (segregated `IFieldPlayerTraining` and `IGoalieTraining`).
* **3.3-DIP:** "Bad Way" (`Coach` class creating concrete players) and "Good Way" (`Coach` class depending on `IPlayer` abstraction).
* **3.4-Refactor-OrderProcessor:** The main chapter refactor showing the "Bad Way" (monolithic `OrderProcessor`) and the "GoodWay" (separated `OrderService` and helper classes).