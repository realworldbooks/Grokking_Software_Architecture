# Chapter 4 Code Examples

This folder contains all the runnable C# code samples for Chapter 4: Thinking in Layers.

Each example is a self-contained .NET 8 Console Application or Solution.

## How to Run

1.  Make sure you have the .NET 8 SDK installed.
2.  For simple projects, navigate into the directory (e.g., `cd 4.2-Downward-Dependency/GoodWay`) and run `dotnet run`.
3.  For the solution in **4.4-Anti-Patterns/GoodWay-RichDomain**, you can open the `MySolution.sln` file in an IDE, or build and run the `MySolution.WebAPI` project from the command line.

See the individual `README.md` files in each folder for specific instructions.

## Examples

* **4.2-Downward-Dependency:** Contains two projects:
    * `/BadWay`: Demonstrates a lower layer improperly calling an upper layer.
    * `/GoodWay`: Shows the correct, loosely coupled approach using DIP.
* **4.3-Cross-Cutting-Concerns:** Contains two projects:
    * `/BadWay`: Demonstrates the "Static Logger" anti-pattern.
    * `/GoodWay`: Shows the correct approach using an `ILogger` interface and dependency injection.
* **4.4-Anti-Patterns:** This folder contains the main event for the chapter.
    * `/BadWay-FatController`: A console app simulating the "Fat Controller" and "Anemic Domain Model" anti-pattern.
    * `/GoodWay-RichDomain`: A complete, multi-project solution demonstrating the correct, layered refactor with a "Rich Domain Model."