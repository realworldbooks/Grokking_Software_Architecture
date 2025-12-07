# Example 5.4: The Server Monitor

This example demonstrates a simple system that monitors server temperature and sends alerts.

* **Goal:** Send an alert when the temperature exceeds 90 degrees.
* **The Challenge:** We want to send SMS alerts in production (Twilio) but only print to the Console during development/testing.

## The "Before" (Anti-Pattern)

* **Location:** `/Before`
* **The Problem:** The `ServerMonitor` class directly instantiates a `TwilioClient`. It is tightly coupled to a specific vendor.
* **Why it fails:** You cannot run this code without "sending" a real SMS (simulated). 
* **Untestable:** You cannot verify the logic without actually sending an SMS (or seeing a side effect).

**How to Run:**
1.  Navigate to the folder:
    ```bash
    cd Before
    ```
2.  Run the application:
    ```bash
    dotnet run
    ```

### Expected Output
You will see the application logic run, followed by an **"Attempted Test"**. This test demonstrates that we cannot verify the result programmatically because we have no access to the internal `TwilioClient`.

```text
--- 1. Running Application Logic ---
Check 80 degrees: [Monitor] Temp 80 is nominal.
Check 95 degrees: [Twilio API] Sending SMS to 555-1234: Server is overheating!

--- 2. Attempting to Test (The Pain) ---
Test Action: Calling CheckTemperature(95)...
Test Assertion: Did it work?
FAIL: We have no way to verify the result programmatically.

