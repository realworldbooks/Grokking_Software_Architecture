# 4.4 Anti-Patterns: Fat Controller vs. Rich Domain

This folder contains the main example for the chapter, contrasting the "Fat Controller" anti-pattern with the correct, refactored "Rich Domain" layered architecture.

* `/BadWay-FatController`: A single console app that simulates the "Fat Controller" / "Anemic Domain Model" anti-pattern.
* `/After-RichDomain`: A complete, multi-project .NET Solution (`.sln`) that demonstrates the correct, layered refactor.

## How to Run

### Bad Way
Navigate to `BadWay-FatController` and run:
```bash
dotnet run

### Good Way
Open MySolution.sln in Visual Studio or VS Code.

Set MySolution.WebAPI as the startup project and run (F5).

OR, from this directory (After-RichDomain), run:

```bash
dotnet run --project MySolution.WebAPI/MySolution.WebAPI.csproj

This will start a web server. You can then use a tool like Postman or curl to send a POST request as shown in the MySolution.WebAPI/README.md.