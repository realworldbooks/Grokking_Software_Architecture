package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after;

import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.domain.PortfolioManager;    
import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.ports.PriceProviderPort;
import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.infrastructure.adapters.CoinGeckoAdapter;
import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.tests.PortfolioTests;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Execution Layer.
 */
public class Demo {
    private static final Logger LOGGER = Logger.getLogger(Demo.class.getName());

    private Demo() { }

    public static void run() {
        LOGGER.info("--- STARTING SCENARIO: CRYPTO TRACKER (AFTER) ---");

        PriceProviderPort realAdapter = new CoinGeckoAdapter(); 
        PortfolioManager manager = new PortfolioManager(realAdapter);

        try {
            double value = manager.calculateTotalValue(2.0);
            LOGGER.log(Level.INFO, "Live Portfolio Value: ${0}", value);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Live API failed, but architecture is safe: {0}", ex.getMessage());
            LOGGER.log(Level.SEVERE, "Exception details", ex);
        }

        LOGGER.info("\n----------------------------------------\n");

        try {
            PortfolioTests.run();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Portfolio tests failed", e);
        }
        
        LOGGER.info("\n========================================");
    }
}