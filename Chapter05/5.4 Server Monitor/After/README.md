## The "After" State (Hexagonal Architecture)

**Location:** `/After`

In this version, we have applied the **Ports and Adapters** pattern.

### The Architecture
* **The Inside (Core):**
    * `ServerMonitor` (Domain): Pure logic. Depends only on `IAlertPort`.
    * `IAlertPort` (Port): An interface defining *what* we need ("SendAlert").
* **The Outside (Infrastructure):**
    * `ConsoleAdapter`: Prints to console (for Dev).
    * `TwilioAdapter`: Talks to the Twilio API (for Prod).

### How to Run

1.  Navigate to the folder:
    ```bash
    cd After
    ```
2.  Run the application:
    ```bash
    dotnet run
    ```

### Expected Output
You will see two phases:

1.  **Application Mode:** Runs the app using the `ConsoleAdapter` (simulating a Dev environment).
2.  **Unit Test Mode:** Runs a deterministic test using a `FakeAlertPort` (Stub) to prove the logic is correct without side effects.

```
text:
--- SERVER MONITOR (HEXAGONAL) ---
1. Application Mode (Dev Adapter):
(DEV ADAPTER) ALERT: Temp is 95 degrees! Take cover!

--- RUNNING UNIT TEST ---
PASS: Alert was received!
```

### Key Files To Explore

* **Core/Ports/IAlertPort.cs:** The boundary line. Notice it has no implementation details.
* **Infrastructure/Adapters/:** Notice how TwilioAdapter and ConsoleAdapter both implement the same port but behave differently.
* **Tests/FakeAlertPort.cs:** A "Test Stub" that captures the message so we can assert against it. This is the secret to testability!
