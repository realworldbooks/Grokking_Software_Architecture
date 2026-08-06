export class PaymentApiClient {
  constructor(healthyDelayMs = 5, failureDelayMs = 300) {
    this.healthyDelayMs = healthyDelayMs;
    this.failureDelayMs = failureDelayMs;
    this.isHealthy = true;
    this.requestCount = 0;
  }

  async charge(orderId) {
    this.requestCount++;
    await new Promise(r => setTimeout(r, this.isHealthy ? this.healthyDelayMs : this.failureDelayMs));
    return this.isHealthy;
  }
}

class NaiveRetryOrderService {
  constructor(paymentApi) { this._paymentApi = paymentApi; }

  async processPaymentAsync(orderId) {
    let retryCount = 0;
    let success = false;
    while (!success && retryCount < 3) {
      try {
        const ok = await this._paymentApi.charge(orderId);
        if (!ok) throw new Error("Payment timeout.");
        success = true;
      } catch (ex) {
        retryCount++;
        console.log("    [Order:" + orderId + "] Payment timeout. Firing retry attempt " + retryCount + "...");
      }
    }
    return success;
  }
}

class CircuitBreaker {
  constructor(failureThreshold = 3, openTimeoutMs = 2000) {
    this.failureThreshold = failureThreshold;
    this.openTimeoutMs = openTimeoutMs;
    this._failureCount = 0;
    this._isOpen = false;
    this._openedAt = null;
  }

  get isOpen() {
    if (this._isOpen && this._openedAt && (Date.now() - this._openedAt) >= this.openTimeoutMs) {
      this._isOpen = false;
      this._failureCount = 0;
    }
    return this._isOpen;
  }
  recordSuccess() { this._failureCount = 0; this._isOpen = false; this._openedAt = null; }
  recordFailure() {
    this._failureCount++;
    if (this._failureCount >= this.failureThreshold) { this._isOpen = true; this._openedAt = Date.now(); }
  }
}

class ResilientPaymentService {
  constructor(paymentApi, maxRetries = 3) {
    this._paymentApi = paymentApi;
    this._maxRetries = maxRetries;
    this._breaker = new CircuitBreaker(3, 2000);
    this.retriesUsed = 0;
    this.fastFailures = 0;
  }

  async _sleepBackoff(attempt) {
    const baseDelay = 100 * Math.pow(2, attempt);
    const jitter = Math.random() * baseDelay;
    await new Promise(r => setTimeout(r, baseDelay + jitter));
  }

  async processPaymentAsync(orderId) {
    if (this._breaker.isOpen) { this.fastFailures++; return false; }
    let attempt = 0;
    while (attempt < this._maxRetries) {
      const ok = await this._paymentApi.charge(orderId);
      if (ok) { this._breaker.recordSuccess(); return true; }
      this._breaker.recordFailure();
      attempt++;
      this.retriesUsed++;
      await this._sleepBackoff(attempt);
    }
    return false;
  }
}

async function simulateUsers(service, userCount) {
  await Promise.all([...Array(userCount).keys()].map(uid => service.processPaymentAsync(uid)));
}

export class Demo {
  static async run() {
    console.log("\n=== Section 12.7.3: The Retry Storm (Node.js) ===");
    console.log("THE SETUP: The Gateway calls an Order Service, which calls a");
    console.log("Payment API. A minor database delay (2 seconds in the book;");
    console.log("compressed to 300ms here so the demo stays snappy) hits the API.");
    console.log("THE BEER GAME: Just like the panicked Wholesaler, the Order Service");
    console.log("assumes the request was lost and triggers automated HTTP Retries.\n");

    console.log("--- PART 1: THE NAIVE LOOP (Listing 12.3) - NO backoff, NO jitter ---\n");
    const paymentApi = new PaymentApiClient(5, 300);
    const naiveService = new NaiveRetryOrderService(paymentApi);
    paymentApi.isHealthy = false;
    console.log("  [Load] 3 users hit the Order Service simultaneously...\n");
    await simulateUsers(naiveService, 3);

    console.log("\n  [Traffic] Payment API received " + paymentApi.requestCount + " requests!");
    console.log("  [Result] 3 users x 3 retries = 9 un-spaced, synchronous HTTP requests!");
    console.log("  [Result] A tiny bit of latency at the edge just amplified into a");
    console.log("  [Result] violent snap at the core - the Payment Database melts.\n");
    console.log("  SCALE IT: With 1,000 users, the naive loop fires 3,000 requests");
    console.log("  instantly. THIS is a Retry Storm - an accidental DDoS of your own");
    console.log("  downstream partner.\n");

    console.log("--- PART 2: THE FIX (Chapter 10 shock absorbers) ---");
    console.log("  Exponential Backoff + Jitter + Circuit Breaker\n");

    const paymentApi2 = new PaymentApiClient(5, 300);
    const resilientService = new ResilientPaymentService(paymentApi2);
    paymentApi2.isHealthy = false;
    console.log("  [Load] The SAME 3 users hit the Order Service...\n");
    await simulateUsers(resilientService, 3);

    console.log("\n  [Traffic] Payment API received " + paymentApi2.requestCount + " requests.");
    console.log("  [Retries] Total retries used: " + resilientService.retriesUsed);
    console.log("  [Breaker] Fast-fail rejections: " + resilientService.fastFailures);
    console.log("  [Result] Each retry waited progressively longer (100ms, 200ms, 400ms");
    console.log("  [Result] + random jitter), giving the API breathing room to recover.");
    console.log("\n  [Note] The circuit breaker trips after 3 CONSECUTIVE failures.");
    console.log("  [Note] In this concurrent demo, all 3 users pass before it opens.");
    console.log("  [Note] In a real system, the breaker would then fail fast for all");
    console.log("  [Note] subsequent requests until the timeout expires.\n");

    console.log("=".repeat(72));
    console.log("ARCHITECTURAL LESSON: DAMPEN THE BULLWHIP EFFECT");
    console.log("-".repeat(72));
    console.log("THE PROBLEM: The naive while loop with no backoff amplifies latency");
    console.log("into a self-inflicted DDoS. The Bullwhip Effect, in code.");
    console.log("\nTHE SHOCK ABSORBERS (from Chapter 10):");
    console.log("  1. CIRCUIT BREAKER: Instantly break out and 'fail fast' when a");
    console.log("     downstream service is struggling, preventing cascading failure.");
    console.log("  2. EXPONENTIAL BACKOFF: Wait progressively longer between retries.");
    console.log("  3. JITTER: Randomize the retry intervals so requests never synchronize.");
    console.log("\nTHE LITMUS TEST: 'No retry loop without a circuit breaker.'");
    console.log("A tiny bit of latency should NEVER be allowed to become a DDoS.");
    console.log("=".repeat(72) + "\n");
  }
}
