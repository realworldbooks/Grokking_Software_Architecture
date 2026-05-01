package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.before;

/**
 * A demonstration of why Tight Coupling ruins testability.
 */
public class AttemptedTest {
    public static void run() {
        System.out.println("\n--- ATTEMPTING TO TEST (BEFORE) ---");
        
        PortfolioManager manager = new PortfolioManager();

        System.out.println("Test Action: Calculating value of 1 BTC...");
        
        try {
            double value = manager.calculateTotalValue(1.0);
            
            // ASSERT
            // We cannot assert equality because the price changes constantly.
            System.out.println("Result: " + value);
            System.out.println("FAIL: This test is FLAKY. We cannot assert a fixed price.");
        } catch (Exception e) {
            System.out.println("CRASH: Test failed completely. No internet connection.");
        }
    }
}