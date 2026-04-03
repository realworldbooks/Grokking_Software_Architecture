package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.before;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * The Execution Layer.
 */
public class Demo {
    private static final Logger logger = Logger.getLogger(Demo.class.getName());

    private Demo() {
        // Private constructor to hide the implicit public one
    }

    public static void run() {
        logger.info("--- STARTING SCENARIO: CRYPTO TRACKER (BEFORE) ---");
        
        PortfolioManager manager = new PortfolioManager();
        
        try {
            logger.info("Calculating live value of 2 BTC... ");
            double value = manager.calculateTotalValue(2.0);
            logger.log(Level.INFO, "Portfolio Value: ${0}", value);
        } catch(Exception ex) {
            logger.log(Level.INFO, "\nFailed. Do you have internet? {0}", ex.getMessage());
        }

        logger.info("\n----------------------------------------");

        AttemptedTest.run();
        
        logger.info("\n========================================");
    }
}