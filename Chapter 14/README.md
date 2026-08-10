# Chapter 14 - Architecting for Quality and Observability

This directory contains runnable examples for Chapter 14 of *Grokking Software Architecture*.

## What's Covered

### Section 14.6 — Unit Test and Instrumentation Logging

Demonstrates Listings 14.1–14.5:

| Listing | Concept | Description |
|---------|---------|-------------|
| **14.1** | PaymentPort | Outbound infrastructure port (IoC boundary contract) |
| **14.2** | OrderService | Instrumented service with MDC / thread-local context |
| **14.3** | Pattern A | Minimal hand-coded stub (lambda in Java, concrete stub elsewhere) |
| **14.4** | Pattern B | Enterprise mocking framework (Mockito / Moq / unittest.mock / node:test) |
| **14.5** | Telemetry Quality Gate | Automated verification of MDC context boundary compliance |

## Language Mappings

| Concept | Java | C# | Python | Node.js |
|---------|------|-----|--------|---------|
| MDC / Thread-Local | `org.slf4j.MDC` | `AsyncLocal<T>` | `contextvars.ContextVar` | `AsyncLocalStorage` |
| Mocking Framework | Mockito | Moq | `unittest.mock` | `node:test` + manual proxy |
| Test Framework | JUnit 5 | xUnit | `unittest` | `node:test` |
| Logging | SLF4J + Logback | Console | `logging` + `OrderIdFormatter` | `console` |

## How to Run

### Python
```bash
cd Python
python menu.py
# Run tests:
python -m unittest discover -s tests
```

### Node.js (JS)
```bash
cd JS
npm start
# Run tests:
npm test
```

### C#
```bash
cd C#
dotnet run
# Run tests:
cd Chapter14.Tests && dotnet test
```

### Java
```bash
cd Java
mvn compile exec:java
# Run tests:
mvn test
```

## Project Structure

```
Chapter 14/
├── README.md
├── C#/
│   ├── Chapter14.csproj
│   ├── Program.cs
│   ├── Examples.json
│   ├── Section_14_6_InstrumentationLogging/
│   │   ├── Demo.cs
│   │   ├── Ports/
│   │   │   ├── IPaymentPort.cs
│   │   │   └── HappyPathPaymentPort.cs
│   │   └── Services/
│   │       └── OrderService.cs
│   └── Chapter14.Tests/
│       ├── Chapter14.Tests.csproj
│       ├── OrderLambdaStubTests.cs
│       ├── OrderMoqMockTests.cs
│       └── OrderTelemetryQualityGateTests.cs
├── Java/
│   ├── pom.xml
│   ├── Examples.json
│   └── src/
│       ├── main/java/com/grokkingsoftwarearchitecture/chapter14/
│       │   ├── Main.java
│       │   └── section_14_6_instrumentation_logging/
│       │       ├── Demo.java
│       │       ├── ports/PaymentPort.java
│       │       └── services/OrderService.java
│       ├── main/resources/
│       │   └── logback.xml
│       └── test/java/com/grokkingsoftwarearchitecture/chapter14/
│           └── section_14_6_instrumentation_logging/
│               ├── OrderLambdaStubTests.java
│               ├── OrderMockitoMockTests.java
│               └── OrderTelemetryQualityGateTests.java
├── JS/
│   ├── menu.js
│   ├── examples.json
│   ├── package.json
│   └── section_14_6_instrumentation_logging/
│       ├── demo.js
│       ├── ports/
│       │   ├── paymentPort.js
│       │   └── happyPathPaymentPort.js
│       ├── services/
│       │   └── orderService.js
│       └── tests/
│           ├── orderLambdaStubTests.js
│           ├── orderMockTests.js
│           └── orderTelemetryQualityGateTests.js
└── Python/
    ├── menu.py
    ├── examples.json
    ├── requirements.txt
    ├── section_14_6_instrumentation_logging/
    │   ├── __init__.py
    │   ├── demo.py
    │   ├── observability/
    │   │   ├── __init__.py
    │   │   └── order_id_filter.py
    │   ├── ports/
    │   │   ├── __init__.py
    │   │   ├── payment_port.py
    │   │   └── happy_path_payment_port.py
    │   └── services/
    │       ├── __init__.py
    │       └── order_service.py
    └── tests/
        ├── test_order_lambda_stub.py
        ├── test_order_mock.py
        └── test_order_telemetry_quality_gate.py
```

## Key Architectural Concepts

- **Inversion of Control (IoC)**: Dependencies are injected via interfaces/ports, enabling isolated testing
- **Mapped Diagnostic Context (MDC)**: Thread-local context sandboxing enriches logs without polluting business signatures
- **Stubs vs. Mocks**: Hand-coded stubs are passive; mocking frameworks verify interaction contracts
- **Telemetry Quality Gates**: Automated tests that verify MDC context is preserved and cleaned up