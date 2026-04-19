package com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.infrastructure.adapters;

import com.grokkingsoftwarearchitecture.chapter10.section_10_3_resilience_decorators.resilient.core.ports.PaymentGateway;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.core.functions.CheckedSupplier;
import java.time.Duration;

/**
 * THE INFRASTRUCTURE ADAPTER (The Implementation):
 * * @description
 * This class encapsulates the Physical Resource Policy for Zebra using Resilience4j. 
 *
 * ARCHITECTURAL CRITIQUE:
 * 1. OBSERVABILITY: We use a custom 'onRetry' event listener to match our 
 * high-rigor JS/C# logging. This ensures every attempt and backoff is 
 * visible in the lab trace.
 * * 2. FAIL-FAST: By throwing the exception immediately on the final attempt, 
 * we prevent "Temporal Leakage" where the system waits for a useless 
 * backoff period before failing over to Plan B.
 */
public class ZebraPaymentAdapter implements PaymentGateway {

    // --- THE PHYSICAL POLICY CONSTANTS (The SLA) ---
    private static final int MAX_RETRIES = 5;
    private static final int MAX_ATTEMPTS = MAX_RETRIES + 1;
    private static final long MIN_DELAY_MS = 2000;
    private static final long MAX_DELAY_MS = 10000;
    private static final double BACKOFF_FACTOR = 2.0;

    private final String baseUrl;
    private final Retry retryShield;

    public ZebraPaymentAdapter(String baseUrl) {
        this.baseUrl = baseUrl;

        // THE SHIELD (Declarative Policy via Resilience4j)
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(MAX_ATTEMPTS)
                .intervalFunction(IntervalFunction.ofExponentialBackoff(
                        MIN_DELAY_MS, 
                        BACKOFF_FACTOR, 
                        MAX_DELAY_MS))
                .retryExceptions(Exception.class)
                .build();

        this.retryShield = Retry.of("zebra-charge-retry", config);

        // LOGGING SHIELD: Synchronizing with the trace
    this.retryShield.getEventPublisher().onRetry(event -> {
            int retryAttempt = event.getNumberOfRetryAttempts();
            System.out.println("      [Retry Shield] Attempt " + retryAttempt + " failed: " + event.getLastThrowable().getMessage() + ".");
            
            // If we have finished our last allowed retry (Attempt 5)
            if (retryAttempt >= MAX_RETRIES) {
                System.err.println("      [Retry Shield] MAX_RETRIES (" + MAX_RETRIES + ") reached. Exhausted.");
            } else {
                System.out.println("      [Retry Shield] Backing off " + event.getWaitInterval().toMillis() + "ms...");
            }
        });
    }

    @Override
    public boolean charge(double amount, String orderId, String idempotencyKey) {
        // FIX: Explicitly type the CheckedSupplier to Boolean to solve the incompatible types error
        CheckedSupplier<Boolean> resilientCall = Retry.decorateCheckedSupplier(retryShield, () -> {
            System.out.println("      [Zebra Adapter] Attempting Zebra Charge for " + orderId + "...");
            
            // SIMULATION: Triggering the shield as seen in your other implementations
            throw new RuntimeException("Zebra API: Gateway Timeout (504)");
        });

        // Convert the checked supplier into an unchecked execution
        try {
            return resilientCall.get();
        } catch (Throwable throwable) {
            // Re-throw as RuntimeException to allow Orchestrator catch block to handle it
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }
}