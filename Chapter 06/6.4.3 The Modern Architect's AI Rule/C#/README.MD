C# / .NET (The Core Book Example)
Path: /AiApi.Demo/README.md (or wherever your C# .csproj lives)

# AI API Demo (C# / .NET)

This is the primary code companion for Chapter 6, demonstrating the architectural principle: **"Your API Schema is the System Prompt."** This project uses standard .NET 8 and Swashbuckle to translate standard C# XML comments (`/// <summary>`) directly into an OpenAPI specification that an AI agent can read.

### Prerequisites
* [.NET 8 SDK](https://dotnet.microsoft.com/download) or newer.

### How to Run
1. Open your terminal and navigate to this folder (where the `.csproj` file is located).
2. Run the application:
   ```bash
   dotnet run
3.Look at the console output for the local URL (usually http://localhost:5000 or https://localhost:5001).
4. Open your browser and navigate to:
http://localhost:<PORT>/swagger

You will see the interactive Swagger UI, and you can inspect how the C# code comments were perfectly translated into instructions for the AI!