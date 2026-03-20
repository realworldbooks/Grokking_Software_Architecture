# Chapter 4 Code Examples

This folder contains all the runnable C# code samples for Chapter 4: Thinking in Layers.

Each example is a self-contained .NET 8 Console Application or Solution.

## How to Run

1.  Make sure you have the .NET 8 SDK installed.
2.  For simple projects, navigate into the directory (e.g., `cd 4.2-Downward-Dependency/After`) and run `dotnet run`.
3.  For the solution in **4.4-Anti-Patterns/After-RichDomain**, you can open the `After.sln` file in an IDE, or build and run the `After.WebAPI` project from the command line.

See the individual `README.md` files in each folder for specific instructions.

## Examples

* **4.2-Downward-Dependency:** Contains two projects:
    * `/Before`: Demonstrates a lower layer improperly calling an upper layer.
    * `/After`: Shows the correct, loosely coupled approach using DIP.
* **4.3-Cross-Cutting-Concerns:** Contains two projects:
    * `/Before`: Demonstrates the "Static Logger" anti-pattern.
    * `/After`: Shows the correct approach using an `ILogger` interface and dependency injection.
* **4.4-Anti-Patterns:** This folder contains the main event for the chapter.
    * `/Before-FatController`: A console app simulating the "Fat Controller" and "Anemic Domain Model" anti-pattern.

    * `/After-RichDomain`: A complete, multi-project solution demonstrating the correct, layered refactor with a "Rich Domain Model."
