# Chapter 10: Architecting for Resilience and Scale

Welcome to the companion code for Chapter 10. When you first learn to code, you are building in a utopia. The database is always online, the network has zero latency, and the third-party API always responds in exactly 12 milliseconds. In the industry, we call this the "Happy Path." 

Chapter 10 focuses on the "Cloud Reality"—a fundamentally hostile, distributed environment where databases time out, third-party APIs crash, and network cables get severed. These examples demonstrate how to build systems that embrace failure, ensuring that when one piece of the system breaks, it degrades gracefully rather than bringing down the entire application.

## Architectural Concepts Covered
1. **Resilience vs. Scaling:**
   - **Scaling** is about handling success (more traffic, more users).
   - **Resilience** is about surviving failure (broken dependencies, network spikes). You cannot scale a fragile system; you will only fail faster.

2. **The Circuit Breaker Pattern:**
   - We implement a state machine (Closed, Open, Half-Open) to protect our system from slow or failing external dependencies. By "failing fast," we prevent threads from piling up and exhausting our server's memory.

3. **Graceful Degradation (Fallbacks):**
   - When the Circuit Breaker trips, the system must have a "Plan B." We demonstrate how to intercept a failure and provide a default response, a cached value, or an offline queue so the user experience is uninterrupted.


## How to Run the Examples

### 1. .NET (C#)
- **Prerequisites:** .NET 8.0 SDK or higher.
- **Tools Used:** - **Polly:** The absolute gold standard in .NET for resilience and transient-fault-handling (Retry, Circuit Breaker, Timeout).
  - **LiteDB:** A lightweight, serverless NoSQL document store used to simulate a local "Fallback Queue" when the external API goes down.
- Navigate to the `Chapter10/C#/` directory.
- Run the interactive console master menu:
  ```bash
  dotnet run
  ```

### 2. Java
- **Prerequisites:** Java 17 and Maven.
- **Tools Used:**
  - **Resilience4j:** A lightweight, modern fault tolerance library designed for Java functional programming (replaces the older Netflix Hystrix).
  - **H2:** An in-memory database used to temporarily persist requests that fail during an Open Circuit state.
- Navigate to the `Chapter10/Java/` directory.
- Compile and execute the interactive master menu:
  ```bash
  mvn clean compile exec:java
  ```

### 3. Node.js (JavaScript)
- **Prerequisites:** Node.js (v18+) and npm.
- **Tools Used:**
  - **RxJS:** Used to create reactive event streams that can elegantly handle timeouts and automatic retries.
  - **node-persist:** A local file-system storage module used to demonstrate caching and offline queueing for Graceful Degradation.
- Navigate to the `Chapter10/JS/` directory.
- Install dependencies and start the menu:
  ```bash
  npm install
  node menu.js
  ```

### 4. Python
- **Prerequisites:** Python 3.12+.
- **Tools Used:** To demonstrate these resilience concepts safely without needing a complex cluster of external servers, the Python implementation utilizes specific architectural libraries:
  - **tenacity:** A highly robust library for adding retry behavior and circuit breaking to Python functions via simple decorators.
  - **persist-queue:** A thread-safe, disk-based queue used to safely park data when the downstream network is unreachable.
  - **requests:** Used for making the synchronous HTTP calls that we will be protecting with our shock absorbers.
- Navigate to the `Chapter10/Python/` directory.
- Create and activate a virtual environment, install dependencies, and run the menu:
  ```bash
  py -m venv .venv
  .\.venv\Scripts\activate      # On Windows
  # source .venv/bin/activate   # On Mac/Linux

  pip install -r requirements.txt
  python menu.py
  ```

## Project Structure
All languages share a unified folder structure. The scenarios are split into isolated "Before" and "After" architectures, but run from a single Master Menu at the root.

```text
├── [Root Menu File]                          # The Master CLI Menu (Program.cs, menu.py, menu.js, etc.)
│
└── section_10_4_circuit_breaker/             # The Circuit Breaker & Fallback implementation
    ├── before/                               # The Fragile Happy Path (Crashes on API timeout)
    │   ├── Application/                      # Tightly coupled logic with no safety nets
    │   ├── Domain/
    │   ├── Infrastructure/
    │   └── Demo                              # Execution scenario showing a cascading failure
    │
    └── after/                                # The Resilient Architecture
        ├── Application/                      # Wrapped with architectural shock absorbers
        ├── Domain/
        ├── Infrastructure/
        └── Demo                              # Execution scenario showing the Circuit Breaker tripping
```

## Feature Comparison Map
| Section | Architectural Goal | The Problem (Before) | The Solution (After) |
| :--- | :--- | :--- | :--- |
| **10.4** | **System Stability** | **Cascading Failures:** A slow third-party API causes your internal threads to wait indefinitely, eventually consuming all memory and crashing your entire server. | **The Circuit Breaker:** The system detects the API is failing and trips the breaker to "Open", instantly returning an error instead of waiting, preserving server resources. |
| **10.4** | **User Experience** | **The Hard Crash:** When a downstream service goes offline, the user is presented with a fatal 500 Internal Server Error and cannot proceed with their workflow. | **Graceful Degradation (Fallback):** The application catches the Open Circuit and activates a Plan B (e.g., serving cached data or queuing a write request to a local database for later sync). |