# Chapter 7 - Event-Driven Architecture

This directory contains runnable examples for Chapter 7 of *Grokking Software Architecture*.

## What's Covered

### Section 7.4 — Event-Driven Architecture (Temporal Decoupling)

Demonstrates the event-driven architectural style using an in-memory broker:

| Component | Description |
|-----------|-------------|
| **`Event`** | Base event contract/interface |
| **`OrderPlaced`** | A concrete domain event carrying order details |
| **`IEventPublisher` / `EventPublisher`** | Core abstraction for publishing events |
| **`OrderController` / `OrderRouter`** | Publishers an `OrderPlaced` event when an order is created |
| **`InMemoryBroker`** | In-memory event broker that decouples producers from consumers (temporal decoupling) |
| **`Consumer` / `ShippingLabelPrinter`** | Subscribers that react to events asynchronously |
| **`Demo`** | Wires everything together and demonstrates the flow |

## Language Mappings

| Concept | Java | C# | Python | Node.js |
|---------|------|-----|--------|---------|
| Event Contract | `Event` interface | `IEvent` interface | `Event` base class | JS class |
| Publisher Port | `EventPublisher` | `IEventPublisher` interface | `EventPublisher` | JS class |
| Broker | `InMemoryBroker` | `InMemoryBroker` | `InMemoryBroker` | RxJS `Subject` |
| Controller | `OrderController` | `OrderController` | `OrderRouter` | JS class |
| Consumer | `Consumer` | `IConsumer` | `Consumer` | RxJS `Subscription` |
| Example Runner | `Demo.java` | `Demo.cs` | `demo.py` | `demo.js` |

## How to Run

### Python
```bash
cd Python
python menu.py
```

### Node.js (JS)
```bash
cd JS
npm install   # first time only (installs RxJS)
npm start
```

### C#
```bash
cd C#
dotnet run
```

### Java
```bash
cd Java
mvn compile exec:java
```

## Project Structure

```
Chapter 07/
├── README.md
├── C#/
│   ├── Chapter07.csproj
│   ├── Program.cs
│   ├── Examples.json
│   └── Section_7_4_Event_Code/
│       ├── Demo.cs
│       ├── Controllers/
│       │   └── OrderController.cs
│       ├── Core/
│       │   └── IEventPublisher.cs
│       ├── Handlers/
│       │   ├── IConsumer.cs
│       │   └── ShippingLabelPrinter.cs
│       ├── Infrastructure/
│       │   └── InMemoryBroker.cs
│       └── Shared/
│           ├── IEvent.cs
│           └── OrderPlaced.cs
├── Java/
│   ├── pom.xml
│   ├── Examples.json
│   └── src/main/java/com/grokkingsoftwarearchitecture/chapter07/
│       ├── Main.java
│       └── section_7_4_event_code/
│           ├── Demo.java
│           ├── controllers/
│           │   └── OrderController.java
│           ├── core/
│           │   └── EventPublisher.java
│           ├── handlers/
│           │   ├── Consumer.java
│           │   └── ShippingLabelPrinter.java
│           ├── infrastructure/
│           │   └── InMemoryBroker.java
│           └── shared/
│               ├── Event.java
│               └── OrderPlaced.java
├── JS/
│   ├── menu.js
│   ├── examples.json
│   ├── package.json
│   └── section_7_4_event_code/
│       └── demo.js
└── Python/
    ├── menu.py
    ├── examples.json
    └── section_7_4_event_code/
        ├── demo.py
        ├── core/
        │   └── event_publisher.py
        ├── handlers/
        │   ├── consumer.py
        │   └── shipping_label_printer.py
        ├── infrastructure/
        │   └── in_memory_broker.py
        ├── routers/
        │   └── order_router.py
        └── shared/
            ├── event.py
            └── order_placed.py
```

## Key Architectural Concepts

- **Event-Driven Architecture**: Components communicate by emitting and consuming events rather than making direct calls
- **Temporal Decoupling**: Producers and consumers are decoupled in time — the publisher doesn't wait for or depend on the consumer's response
- **Publish/Subscribe**: The broker routes events to all interested subscribers, allowing new consumers to be added without changing producers
- **Domain Events**: `OrderPlaced` represents a meaningful business occurrence that other parts of the system can react to
- **Ports & Adapters**: The `IEventPublisher`/`EventPublisher` abstraction allows swapping the in-memory broker for a real message queue (e.g., Kafka, RabbitMQ) without changing business logic