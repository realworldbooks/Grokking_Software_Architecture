## The "After" State (Hexagonal Architecture)

**Location:** `/After`

In this version, we have applied the **Ports and Adapters** pattern to decouple the logic from the internet.

### The Architecture
* **The Inside (Core):**
    * `PortfolioManager`: Pure logic. Depends only on `IPriceProviderPort`.
    * `IPriceProviderPort`: An interface defining *what* we need ("GetBitcoinPrice").
* **The Outside (Infrastructure):**
    * `CoinGeckoAdapter`: The real implementation that calls the API.
    * `FakePriceProvider`: A test stub that always returns $50,000.

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
1.  **Simulation:** Runs using the Fake Adapter (prints "Simulated Price: $50,000").
2.  **Unit Test:** Passes successfully, proving the math is correct (`2.5 * 50,000 = 125,000`).

```
text:
--- CRYPTO TRACKER (HEXAGONAL) ---
Simulated Price: $50,000
Portfolio Value: $125,000

--- RUNNING PORTFOLIO UNIT TEST ---
PASS: Calculated correct value offline!
```
### Key Files to Explore
* **Core/Ports/IPriceProviderPort.cs:** The interface that breaks the dependency.
* **Infrastructure/Adapters/FakePriceProvider.cs:** The "Stub" adapter used for offline testing.
* **Tests/PortfolioTests.cs:** The proof that our logic is now testable.