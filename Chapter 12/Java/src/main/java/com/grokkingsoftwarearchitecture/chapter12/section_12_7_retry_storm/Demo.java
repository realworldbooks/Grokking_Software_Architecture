package com.grokkingsoftwarearchitecture.chapter12.section_12_7_retry_storm;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Demo {

    // ------------------------------------------------------------------
    // The Downstream "Payment API"
    // ------------------------------------------------------------------

    static class PaymentApiClient {
        volatile boolean healthy = true;
        int requestCount = 0;

        boolean charge() {
            requestCount++;
            try { Thread.sleep(healthy ? 5 : 300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return healthy;
        }
    }

    // ------------------------------------------------------------------
    // THE ANTI-PATTERN (Listing 12.3) — Naive Retry with NO backoff, NO jitter
    // ------------------------------------------------------------------

    static class NaiveRetryOrderService {
        private final PaymentApiClient api;

        NaiveRetryOrderService(PaymentApiClient api) { this.api = api; }

        boolean processPayment(int orderId) {
            int retry = 0;
            boolean ok = false;
            while (!ok && retry < 3) {
                try {
                    if (!api.charge()) throw new RuntimeException("Timeout.");
                    ok = true;
                } catch (RuntimeException e) {
                    retry++;
                    System.out.println("    [Order:" + orderId + "] Payment timeout. Firing retry attempt " + retry + "...");
                }
            }
            return ok;
        }
    }

    // ------------------------------------------------------------------
    // THE FIX — Exponential Backoff + Jitter + Circuit Breaker
    // ------------------------------------------------------------------

    static class CircuitBreaker {
        private final int failureThreshold;
        private final long openTimeoutMs;
        private int failureCount = 0;
        private boolean isOpen = false;
        private long openedAt = 0;

        CircuitBreaker(int failureThreshold, long openTimeoutMs) {
            this.failureThreshold = failureThreshold;
            this.openTimeoutMs = openTimeoutMs;
        }

        boolean isOpen() {
            if (isOpen && openedAt > 0 && (System.currentTimeMillis() - openedAt) >= openTimeoutMs) {
                isOpen = false;
                failureCount = 0;
            }
            return isOpen;
        }

        void recordSuccess() {
            failureCount = 0;
            isOpen = false;
        }

        void recordFailure() {
            failureCount++;
            if (failureCount >= failureThreshold) {
                isOpen = true;
                openedAt = System.currentTimeMillis();
            }
        }
    }

    static class ResilientPaymentService {
        private final PaymentApiClient api;
        private final int maxRetries;
        private final CircuitBreaker breaker;
        private final Random rng = ThreadLocalRandom.current();

        int retriesUsed = 0;
        int fastFailures = 0;

        ResilientPaymentService(PaymentApiClient api, int maxRetries) {
            this.api = api;
            this.maxRetries = maxRetries;
            this.breaker = new CircuitBreaker(3, 2000);
        }

        void sleepBackoff(int attempt) {
            // Exponential backoff: 100ms, 200ms, 400ms... plus jitter.
            double baseDelay = 100 * Math.pow(2, attempt);
            double jitter = rng.nextDouble() * baseDelay;
            try { Thread.sleep((long) (baseDelay + jitter)); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        boolean processPayment(int orderId) {
            if (breaker.isOpen()) {
                // FAIL FAST — the breaker has tripped. No request hits the wire.
                fastFailures++;
                return false;
            }

            int attempt = 0;
            while (attempt < maxRetries) {
                boolean ok = api.charge();
                if (ok) {
                    breaker.recordSuccess();
                    return true;
                }

                // Failure — record it and wait with backoff + jitter.
                breaker.recordFailure();
                attempt++;
                retriesUsed++;
                sleepBackoff(attempt);
            }
            return false;
        }
    }

    // ------------------------------------------------------------------
    // Simulation helper — run N "users" concurrently and count the traffic
    // ------------------------------------------------------------------

    static void simulateUsers(NaiveRetryOrderService service, int userCount) throws InterruptedException {
        Thread[] users = new Thread[userCount];
        for (int i = 0; i < userCount; i++) {
            final int uid = i;
            users[i] = new Thread(() -> service.processPayment(uid));
            users[i].start();
        }
        for (Thread t : users) {
            try { t.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    static void simulateUsers(ResilientPaymentService service, int userCount) throws InterruptedException {
        Thread[] users = new Thread[userCount];
        for (int i = 0; i < userCount; i++) {
            final int uid = i;
            users[i] = new Thread(() -> service.processPayment(uid));
            users[i].start();
        }
        for (Thread t : users) {
            try { t.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    // ------------------------------------------------------------------
    // Demo
    // ------------------------------------------------------------------

    public static void run() throws Exception {
        System.out.println("\n=== Section 12.7.3: The Retry Storm (Java) ===");
        System.out.println("THE SETUP: The Gateway calls an Order Service, which calls a");
        System.out.println("Payment API. A minor database delay (2 seconds in the book;");
        System.out.println("compressed to 300ms here so the demo stays snappy) hits the API.");
        System.out.println("THE BEER GAME: Just like the panicked Wholesaler, the Order Service");
        System.out.println("assumes the request was lost and triggers automated HTTP Retries.\n");

        // ------------------------------------------------------------------
        // PART 1: THE NAIVE LOOP (Listing 12.3) — Retry Storm
        // ------------------------------------------------------------------
        System.out.println("--- PART 1: THE NAIVE LOOP (Listing 12.3) — NO backoff, NO jitter ---\n");

        PaymentApiClient api = new PaymentApiClient();
        NaiveRetryOrderService svc = new NaiveRetryOrderService(api);
        api.healthy = false;
        System.out.println("  [Load] 3 users hit the Order Service simultaneously...\n");

        simulateUsers(svc, 3);

        System.out.println("\n  [Traffic] Payment API received " + api.requestCount + " requests!");
        System.out.println("  [Result] 3 users x 3 retries = 9 un-spaced, synchronous HTTP requests!");
        System.out.println("  [Result] A tiny bit of latency at the edge just amplified into a");
        System.out.println("  [Result] violent snap at the core — the Payment Database melts.\n");
        System.out.println("  SCALE IT: With 1,000 users, the naive loop fires 3,000 requests");
        System.out.println("  instantly. THIS is a Retry Storm — an accidental DDoS of your own");
        System.out.println("  downstream partner.\n");

        // ------------------------------------------------------------------
        // PART 2: THE FIX — Exponential Backoff + Jitter + Circuit Breaker
        // ------------------------------------------------------------------
        System.out.println("--- PART 2: THE FIX (Chapter 10 shock absorbers) ---");
        System.out.println("  Exponential Backoff + Jitter + Circuit Breaker\n");

        PaymentApiClient api2 = new PaymentApiClient();
        ResilientPaymentService resilientService = new ResilientPaymentService(api2, 3);
        api2.healthy = false;
        System.out.println("  [Load] The SAME 3 users hit the Order Service...\n");

        simulateUsers(resilientService, 3);

        System.out.println("\n  [Traffic] Payment API received " + api2.requestCount + " requests.");
        System.out.println("  [Retries] Total retries used: " + resilientService.retriesUsed);
        System.out.println("  [Breaker] Fast-fail rejections (no network call): " + resilientService.fastFailures);
        System.out.println("  [Result] Each retry waited progressively longer (100ms, 200ms, 400ms");
        System.out.println("  [Result] + random jitter), giving the API breathing room to recover.");
        System.out.println();
        System.out.println("  [Note] The circuit breaker trips after 3 CONSECUTIVE failures.");
        System.out.println("  [Note] In this concurrent demo, all 3 users pass the breaker before");
        System.out.println("  [Note] it opens. In a real system, the breaker would then fail fast");
        System.out.println("  [Note] for ALL subsequent requests until the timeout expires.\n");

        System.out.println("=".repeat(72));
        System.out.println("ARCHITECTURAL LESSON: DAMPEN THE BULLWHIP EFFECT");
        System.out.println("-".repeat(72));
        System.out.println("THE PROBLEM: The naive while loop with no backoff amplifies latency");
        System.out.println("into a self-inflicted DDoS. The Bullwhip Effect, in code.");
        System.out.println();
        System.out.println("THE SHOCK ABSORBERS (from Chapter 10):");
        System.out.println("  1. CIRCUIT BREAKER: Instantly break out and 'fail fast' when a");
        System.out.println("     downstream service is struggling, preventing cascading failure.");
        System.out.println("  2. EXPONENTIAL BACKOFF: Wait progressively longer between retries,");
        System.out.println("     giving the overwhelmed system 'breathing room' to recover.");
        System.out.println("  3. JITTER: Randomize the retry intervals so requests never");
        System.out.println("     synchronize into a thundering herd.");
        System.out.println();
        System.out.println("THE LITMUS TEST: 'No retry loop without a circuit breaker.'");
        System.out.println("A tiny bit of latency should NEVER be allowed to become a DDoS.");
        System.out.println("=".repeat(72) + "\n");
    }
}
