package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after;

import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.domain.PortfolioManager;    
import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.ports.PriceProviderPort;
import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.infrastructure.adapters.CoinGeckoAdapter;
import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.tests.PortfolioTests;

/**
 * The Execution Layer.
 */
public class Demo {

    private Demo() { }

    public static void run() {
        System.out.println("--- STARTING SCENARIO: CRYPTO TRACKER (AFTER) ---");

        PriceProviderPort realAdapter = new CoinGeckoAdapter(); 
        PortfolioManager manager = new PortfolioManager(realAdapter);

        try {
            double value = manager.calculateTotalValue(2.0);
            System.out.println("Live Portfolio Value: $" + value);
        } catch (Exception ex) {
            System.err.println("Live API failed, but architecture is safe: " + ex.getMessage());
            ex.printStackTrace();
        }

        System.out.println("\n----------------------------------------\n");

        try {
            PortfolioTests.run();
        } catch (Exception e) {
            System.err.println("Portfolio tests failed");
            e.printStackTrace();
        }
        
        System.out.println("\n========================================");
    }
}