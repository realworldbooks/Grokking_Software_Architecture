# Chapter 5: Hexagonal Architecture (Ports and Adapters)

Welcome to the companion code for Chapter 5. In Chapter 4, we explored Layered Architecture; now, we evolve those boundaries into Hexagonal Architecture. This pattern, also known as Ports and Adapters, focuses on the absolute isolation of the Core Domain from the "chaotic" outside world of infrastructure, UI, and third-party APIs.

These examples demonstrate how to create a "Plug-and-Play" architecture where business logic remains identical whether it is triggered by a CLI, a Unit Test, or a real-world Cloud Service.

Welcome to the companion code for Chapter 5. In Chapter 4, we explored Layered Architecture; now, we evolve those boundaries into Hexagonal Architecture. This pattern, also known as Ports and Adapters, focuses on the absolute isolation of the Core Domain from the "chaotic" outside world of infrastructure, UI, and third-party APIs.

These examples demonstrate how to create a "Plug-and-Play" architecture where business logic remains identical whether it is triggered by a CLI, a Unit Test, or a real-world Cloud Service.

## Architectural Concepts Covered
1. The Golden Rule of Isolation:
    - The Core Domain contains zero references to external technologies (No SQL, No HTTP, No Twilio, No Kafka).
    - Business rules are defined using pure language features and internal constants.

2. Ports (The Interfaces):
    - Driven Ports: Defined by the Core to specify what it needs from the outside world (e.g., "I need a price" or "I need to send an alert").
    - Driving Ports: The entry points (APIs/UIs) that trigger the Core logic.

3. Adapters (The Implementation):
    - Infrastructure Adapters: Real-world implementations like CoinGeckoAdapter (HTTP) or TwilioAdapter (SMS).
    - Test Adapters (Fakes): High-speed, deterministic "Airplane Mode" implementations that allow for 100% stable automated testing.

4. Dependency Inversion Principle (DIP) at the Edge:
    - Using Constructor Injection to "plug" an adapter into a port at runtime.
    - This prevents the Core from "new-ing up" its own dependencies, solving the Tight Coupling problem.

## How to Run the Examples
1. .NET (C#)
    - Prerequisites: .NET 6.0 SDK or higher.
    - Navigate to the Chapter05/C#/ directory.
    - Run the interactive console menu:

```Bash
dotnet run
```

2. Java
    - Prerequisites: Java 17 and Maven.
    - Navigate to the Chapter05/Java/ directory.
    - Compile and execute the interactive menu:

```Bash
mvn clean compile exec:java
```

3. Node.js (JavaScript)
    - Prerequisites: Node.js (v18+).
    - Navigate to the Chapter05/JS/ directory.
    - Run the interactive menu:

```Bash
node menu.js

Note: This implementation uses Vanilla JSDoc for documentation and standard CommonJS modules.
```

4. Python
    - Prerequisites: Python 3.10+.
    - Navigate to the Chapter05/Python/ directory.
    - Run the interactive menu:

```Bash
python menu.py
```

## Project Structure
The folders are organized by section. Each scenario contains a before/ (Tightly Coupled) and after/ (Hexagonal) implementation.

```Plaintext
├── section_5_4_server_monitor/       # Alerting via SMS, Kafka, or Console
│   |── after/                        # Hexagonal (Ports & Adapters)
│   ├── before/                       # Tight Coupling (Direct SDK usage)
├── section_5_6_crypto_tracker/       # Financial math with external Price APIs
│   |── after/                        # Deterministic Fakes (Isolated Core)
│   ├── before/                       # Flaky Tests (Dependencies on live APIs)
```

### Feature Comparison Map

| Section | Architectural Goal | The Problem (Before) | The Solution (After) |
| :--- | :--- | :--- | :--- |
| **5.4** | **Infrastructure Agnostic** | **Tight Coupling:** The monitor is hard-coded to a specific SMS provider. | **Ports:** The Core calls an `AlertPort`. We swap SMS for Kafka or Console without touching Core code. |
| **5.6** | **Deterministic Testing** | **Flaky Tests:** Unit tests fail if the internet is down or the Bitcoin price changes. | **Fakes:** We inject a `FakePriceProvider` that returns a fixed value, ensuring 100% stable assertions. |
| **Logic** | **Separation of Concerns** | **Leaky Abstractions:** Third-party SDKs and JSON parsing logic live inside business methods. | **Encapsulation:** Messy API details are hidden inside Adapters; the Core only sees clean objects. |