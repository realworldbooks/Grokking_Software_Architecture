# Chapter 4: Layered Architecture and the Domain

Welcome to the companion code for Chapter 4. While Chapter 3 focused on the foundational principles of sound design (SOLID, Cohesion, Coupling), Chapter 4 scales those concepts up to the architectural level. 

These examples demonstrate how to structure a layered application, manage dependencies between those layers, and avoid common structural traps like the "Fat Controller" and "Anemic Domain" anti-patterns.

## Architectural Concepts Covered
1. **Dependency Management:**
   - We explore the critical difference between Upward Dependencies (where lower layers dictate terms to higher layers) and Downward Dependencies (where the core domain remains isolated and pure).
   - How to prevent database infrastructure from leaking into business logic.

2. **Cross-Cutting Concerns & Abstractions:**
   - Applying the Dependency Inversion Principle (DIP) at scale to inject external services (like Email Providers or Logging) without tightly coupling your application to specific technologies.

3. **The Web API Anti-Patterns:**
   - **The Fat Controller:** A monolithic endpoint that mixes HTTP orchestration, database queries, and business rules into a single file.
   - **The Anemic Domain:** Data entities that are nothing more than "bags of getters and setters" devoid of behavior.
   - **The Traditional 4-Layer Architecture Solution:** Moving logic into a Rich Domain Model, leaving a "Thin Controller" whose only job is translating HTTP requests into domain commands.

4. **Live Web Frameworks & Swagger UI:**
   - The Chapter 4 "After" examples launch actual web servers (ASP.NET Core, Spring Boot, Express, and FastAPI) complete with interactive OpenAPI/Swagger documentation.

## How to Run the Examples

### 1. .NET (C#)
- **Prerequisites:** .NET 6.0 SDK or higher.
- Navigate to the `Chapter04/C#/` directory.
- Run the interactive console menu:
  ```bash
  dotnet run
  ```

### 2. Java
- **Prerequisites:** Java 17 and Maven.
- Navigate to the Chapter04/Java/ directory.
- Compile and execute the interactive menu:
  ```Bash
  mvn clean compile exec:java
  ```

### 3. Node.js (JavaScript)
- **Prerequisites:** Node.js (v16+) and npm.
- Navigate to the Chapter04/JS/ directory.
- Install the web dependencies (express, swagger-ui-express) and start the menu:
```Bash
npm install
npm start
(Alternatively, run node menu.js directly).
```
### 4. Python
- **Prerequisites:** Python 3.10+ (3.12 recommended).
- Navigate to the Chapter04/Python/ directory.
- Create and activate a virtual environment, install the web dependencies (fastapi, uvicorn), and run the menu:
```Bash
py -m venv venv
.\venv\Scripts\activate   # On Windows
# source venv/bin/activate # On Mac/Linux
pip install -r requirements.txt
python menu.py
```
Note: Ensure you have `__init__.py` files in your subdirectories so the dynamic import system can locate the demo modules.

## Project Structure
The folders are organized by section number. Each scenario contains a before/ (Anti-Pattern) and after/ (Clean Architecture) implementation.

```Plaintext
├── section_4_2_downward_dependency/         # Layering and Dependency Direction
├── section_4_3_cross_cutting_concerns/      # Dependency Inversion at Scale
└── section_4_4_anti_patterns/               # The Fat Controller & Rich Domain
    ├── before_fat_controller_anemic_domain/
    └── after_rich_domain_thin_controller/
```

## Feature Comparison Map

| Section | Architectural Goal | The Problem (Before) | The Solution (After) |
| :--- | :--- | :--- | :--- |
| **4.2** | **Protect the Domain Layer** | **Upward Dependency:** The core business rules rely on specific database implementations. | **Downward Dependency:** The Presentation and Infrastructure layers depend on the Core. |
| **4.3** | **Decouple External Services** | **Missing Abstraction:** Business logic is hardcoded to specific third-party tools (e.g., SMTP). | **Dependency Inversion:** Interfaces dictate the contract; external tools are injected. |
| **4.4** | **Enforce the Single Responsibility Principle** | **Fat Controller:** The Presentation layer handles HTTP routing, DB queries, and business math. | **Thin Controller:** The controller only routes data. Business rules live inside the entities. |
| **4.4** | **Unify Data and Behavior** | **Anemic Domain:** Models are just simple DTOs holding state without validation. | **Rich Domain:** Models encapsulate their own data and protect their invariants. |