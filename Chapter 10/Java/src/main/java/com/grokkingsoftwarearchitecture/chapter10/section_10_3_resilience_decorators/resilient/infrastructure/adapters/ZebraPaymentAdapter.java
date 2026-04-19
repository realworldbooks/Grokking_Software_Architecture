package com.grokking.resilience.infrastructure.adapters;

import com.grokking.resilience.core.ports.PaymentGateway;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import java.time.Duration;
import java.util.function.Supplier;

/**
 * THE INFRASTRUCTURE ADAPTER:
 * * ARCHITECTURAL CRITIQUE:
 * This file encapsulates the Physical Resource Policy. By moving every 
 * configuration value into named constants, we transform a "hidden script" 
 * into a documented Service Level Agreement (SLA).
 */
public class ZebraPaymentAdapter implements PaymentGateway {

    // --- THE PHYSICAL POLICY CONSTANTS (The SLA) ---
    private static final int CONNECT_TIMEOUT_SECONDS = 2;
    private static final int READ_TIMEOUT_SECONDS = 8;
    
    private static final int MAX_RETRIES = 5;
    private static final int INITIAL_RETRY_DELAY_SECONDS = 2;
    private static final double BACKOFF_MULTIPLIER = 2.0;
    private static final int MAX_RETRY_DELAY_SECONDS = 10;

    private final String baseUrl;
    private final Retry retryShield;

    public ZebraPaymentAdapter(String baseUrl) {
        this.baseUrl = baseUrl;

        // THE SHIELD: Declarative Policy via Resilience4j
        // #SENIOR NOTE: Notice how we map the raw constants into 'Duration' objects 
        // here. This makes the configuration readable and type-safe.
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(MAX_RETRIES)
                .waitDuration(Duration.ofSeconds(INITIAL_RETRY_DELAY_SECONDS))
                .backoffConfig(
                    io.github.resilience4j.core.IntervalFunction
                        .ofExponentialBackoff(
                            Duration.ofSeconds(INITIAL_RETRY_DELAY_SECONDS), 
                            BACKOFF_MULTIPLIER, 
                            Duration.ofSeconds(MAX_RETRY_DELAY_SECONDS)
                        )
                )
                .retryExceptions(Exception.class)
                .build();

        this.retryShield = Retry.of("zebra-charge-retry", config);
    }

    @Override
    public boolean charge(double amount, String orderId, String idempotencyKey) {
        Supplier<Boolean> resilientCall = Retry.decorateSupplier(retryShield, () -> {
            System.out.println("      [Zebra Adapter] Attempting Order " + orderId + "...");
            
            /* * TEACHING NOTE:
             * A real HttpClient would use CONNECT_TIMEOUT_SECONDS and 
             * READ_TIMEOUT_SECONDS to ensure we never hang the thread pool.
             */
            return true; 
        });

        return resilientCall.get();
    }
}