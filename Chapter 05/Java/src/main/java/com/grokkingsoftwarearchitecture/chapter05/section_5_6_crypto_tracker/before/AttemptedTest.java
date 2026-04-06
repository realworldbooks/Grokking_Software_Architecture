package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.before;

import com.grokkingsoftwarearchitecture.chapter05.shared.LogManager;

/**
 * A demonstration of why Tight Coupling ruins testability.
 */
public class AttemptedTest {
    public static void run() {
        LogManager.info(AttemptedTest.class, "\n--- ATTEMPTING TO TEST (BEFORE) ---");
        
        PortfolioManager manager = new PortfolioManager();

        LogManager.info(AttemptedTest.class, "Test Action: Calculating value of 1 BTC...");
        
        try {
            double value = manager.calculateTotalValue(1.0);
            
            // ASSERT
            // We cannot assert equality because the price changes constantly.
            LogManager.info(AttemptedTest.class, "Result: {0}", value);
            LogManager.info(AttemptedTest.class, "FAIL: This test is FLAKY. We cannot assert a fixed price.");
        } catch (Exception e) {
            LogManager.info(AttemptedTest.class, "CRASH: Test failed completely. No internet connection.");
        }
    }
}