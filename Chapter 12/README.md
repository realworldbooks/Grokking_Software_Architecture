# Chapter 12: The Microservice Premium - The Network Tax

Welcome to the companion code for Chapter 12. This chapter explores the hidden costs of breaking a monolith into microservices. The business requirement does not change - you still need to fetch a Blog Post, the Author's profile, and the recent Comments to render a webpage - but the engineering complexity skyrockets when that data is spread across three separate services on the network.

These examples demonstrate the **Network Tax** in action: the Latency Tax, the Reliability Tax, and the Partial Failure Tax. They also demonstrate the **Retry Storm** - how a naive retry loop can accidentally launch a Distributed Denial of Service (DDoS) attack against your own downstream partner.

## Architectural Concepts Covered

1. **The Monolith Approach (Listing 12.1):**
   - **One Database, One Query:** All data lives in a single database. A single SQL JOIN returns the post, author, and comments together.
   - **Zero Network Latency:** There are no HTTP calls between services. Execution time: ~5 ms.
   - **Predictable Failure:** If the database is down, the whole query fails together. No partial state, no half-rendered page.

2. **The Microservice Aggregator (Listing 12.2):**
   - **TAX 1 - The Latency Tax:** You must fire off three separate HTTP network calls across the wire.
   - **TAX 2 - The Reliability Tax:** You must wait for the network, which might drop packets or time out. This forces async/await and `Task.WhenAll`.
   - **TAX 3 - Handling Partial Failures:** If the comments service crashes but the post service succeeds, you must manually write fallback logic to serve the page without comments.

3. **The Retry Storm (Listing 12.3):**
   - **The Bullwhip Effect in Code:** A minor 2-second database delay causes naive retry loops to multiply traffic, accidentally launching a DDoS against your own database.
   - **The Shock Absorbers (from Chapter 10):**
     - **Circuit Breaker:** Instantly break out of the retry loop and "fail fast" when a downstream service is struggling.
     - **Exponential Backoff:** Wait progressively longer between retries, giving the overwhelmed system "breathing room" to recover.
     - **Jitter:** Add a random component to retry intervals so requests never synchronize into a thundering herd.

## How to Run the Examples

### 1. .NET (C#)
- **Prerequisites:** .NET 8.0 SDK or higher.
- Navigate to the `Chapter12/C#/` directory.
- Run the interactive console master menu:
  ```bash
  dotnet run
  ```

### 2. Java
- **Prerequisites:** Java 17 and Maven.
- **Tools Used:**
  - **Gson:** A JSON library used for parsing the Examples.json configuration.
- Navigate to the `Chapter12/Java/` directory.
- Compile and execute the interactive master menu:
  ```bash
  mvn clean compile exec:java
  ```

### 3. Node.js (JavaScript)
- **Prerequisites:** Node.js (v18+).
- Navigate to the `Chapter12/JS/` directory.
- Start the menu:
  ```bash
  node menu.js
  ```

### 4. Python
- **Prerequisites:** Python 3.12+.
- **Tools Used:** No external dependencies. All examples use only the Python standard library (`asyncio`, `dataclasses`, `time`, `random`).
- Navigate to the `Chapter12/Python/` directory.
- Run the menu:
  ```bash
  python menu.py
  ```

## Project Structure
All languages share a unified folder structure. The scenarios are split into the "Before" (Monolith) and "After" (Microservice Aggregator) architectures, plus the Retry Storm lesson, all run from a single Master Menu at the root.

```text
├── [Root Menu File]                          # The Master CLI Menu (Program.cs, menu.py, menu.js, Main.java, etc.)
│
├── section_12_6_network_tax/                 # The Network Tax in Action - The Blog Endpoint
│   ├── monolith/                             # Listing 12.1: The Monolith Approach (Simple and Fast)
│   │   ├── Demo                              # Execution scenario showing the monolith's single JOIN
│   │   ├── MockBlogDatabase                  # Simulated DB with all data in one place
│   │   └── MonolithBlogEndpoint              # The endpoint that fetches everything in ONE query
│   │
│   └── microservices/                        # Listing 12.2: The Microservice Aggregator
│       ├── Demo                              # Execution scenario showing the 3 HTTP calls + fallbacks
│       ├── MockClients                       # Simulated PostService, AuthorService, CommentService
│       └── AggregatorGateway                 # The API Gateway that collects the page pieces
│
└── section_12_7_retry_storm/                 # Listing 12.3: The Retry Storm
    └── Demo                                  # Naive retry loop vs. Exponential Backoff + Jitter + Circuit Breaker
```

## Feature Comparison Map
| Section | Architectural Goal | The Problem (Before) | The Solution (After) |
| :--- | :--- | :--- | :--- |
| **12.6.1** | **Simplicity** | **The Monolith:** One database, one query, zero network latency. Execution time: ~5 ms. | **The Microservice:** 3 separate HTTP calls across the wire. Each call adds serialization, deserialization, and network overhead. |
| **12.6.2** | **Reliability** | **Predictable Failure:** If the database is down, the whole query fails together. No partial state. | **Partial Failures:** You must manually write fallback logic for when one service crashes but the others survive. |
| **12.7.3** | **Resilience** | **The Retry Storm:** A naive retry loop with no backoff amplifies latency into a self-inflicted DDoS. | **The Shock Absorbers:** Circuit Breaker + Exponential Backoff + Jitter dampen the Bullwhip Effect. |