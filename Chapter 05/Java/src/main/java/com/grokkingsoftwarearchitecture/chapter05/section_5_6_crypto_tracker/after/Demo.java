package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after;

import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.domain.PortfolioManager;    
import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.ports.PriceProviderPort;
import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.infrastructure.adapters.CoinGeckoAdapter;
import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.tests.PortfolioTests;
import com.grokkingsoftwarearchitecture.chapter05.shared.LogManager;

import java.util.logging.Level;

/**
 * The Execution Layer.
 */
public class Demo {

    private Demo() { }

    public static void run() {
        LogManager.info(Demo.class, "--- STARTING SCENARIO: CRYPTO TRACKER (AFTER) ---");

        PriceProviderPort realAdapter = new CoinGeckoAdapter(); 
        PortfolioManager manager = new PortfolioManager(realAdapter);

        try {
            double value = manager.calculateTotalValue(2.0);
            LogManager.info(Demo.class, "Live Portfolio Value: ${0}", value);
        } catch (Exception ex) {
            LogManager.getLogger(Demo.class).log(Level.SEVERE, "Live API failed, but architecture is safe: {0}", ex.getMessage());
            LogManager.getLogger(Demo.class).log(Level.SEVERE, "Exception details", ex);
        }

        LogManager.info(Demo.class, "\n----------------------------------------\n");

        try {
            PortfolioTests.run();
        } catch (Exception e) {
            LogManager.getLogger(Demo.class).log(Level.SEVERE, "Portfolio tests failed", e);
        }
        
        LogManager.info(Demo.class, "\n========================================");
    }
}