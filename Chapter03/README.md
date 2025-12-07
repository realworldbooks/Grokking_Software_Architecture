# Chapter 3 Code Examples

This folder contains all the runnable C# code samples for Chapter 3: The Principles of Sound Structure.

Each example is a self-contained .NET 8 Console Application.

## How to Run

1.  Make sure you have the .NET 8 SDK installed.
2.  Navigate into a specific example's directory (e.g., `cd 3.3-SRP/After`).
3.  Run the application: `dotnet run`

## Examples

* **3.2-Coupling-Exercise:** The `UserReportGenerator` example for the coupling exercise.
* **3.3-SRP:** "Before" (monolithic `Player`) and "After" (separated `Player`, `TacticsEngine`, `PlayerRepository`).
* **3.3-OCP:** "Before" (if/else `ExecutePlay`) and "After" (using the `IPlay` interface).
* **3.3-LSP:** "Before" (deceptive `Goalie` class) and "After" (correct `Midfielder` substitute).
* **3.3-ISP:** "Before" ("fat" `ITrainingSession`) and "After" (segregated `IFieldPlayerTraining` and `IGoalieTraining`).
* **3.3-DIP:** "Before" (`Coach` class creating concrete players) and "After" (`Coach` class depending on `IPlayer` abstraction).
* **3.4-Refactor-OrderProcessor:** The main chapter refactor showing the "Before" (monolithic `OrderProcessor`) and the "After" (separated `OrderService` and helper classes).