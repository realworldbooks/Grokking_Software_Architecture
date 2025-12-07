# Example 5.6: Crypto Portfolio Tracker

This is the "In Action" refactoring exercise from the book. It calculates the total value of a Bitcoin portfolio in USD.

* **Goal:** Calculate the current value of a portfolio (e.g., 2.5 BTC).
* **Requirement:** Fetch the real-time price of Bitcoin from an external API (CoinGecko).
* **Constraint:** We need to be able to verify the math logic even if the API is down or the internet is disconnected.

---

## The "Before" State (Tightly Coupled)

**Location:** `/Before`

In this version, the `PortfolioManager` creates a `new HttpClient()` inside the logic and calls the live CoinGecko API directly.

### The Problems
1.  **Fragile:** If you turn off your internet, this app crashes.
2.  **Non-Deterministic Tests:** The price of Bitcoin changes every second. You can never write an assertion like `Assert.Equal(50000, price)` because the live price will never exactly match your hard-coded expected value.

### How to Run

1.  Navigate to the folder:
    ```bash
    cd Before
    ```
2.  Run the application:
    ```bash
    dotnet run
    ```

### Expected Output
It prints the current *live* price of Bitcoin (if you are online). It then runs an **"Attempted Test"** that usually fails or is flaky because the price is never exactly what we hard-coded.

```
text:
Portfolio Value: $234,567.89

--- ATTEMPTING TO TEST (BEFORE) ---
FAIL: Expected 50,000 but got 94,213.55.
      (Test is flaky because we depend on live data.)
```