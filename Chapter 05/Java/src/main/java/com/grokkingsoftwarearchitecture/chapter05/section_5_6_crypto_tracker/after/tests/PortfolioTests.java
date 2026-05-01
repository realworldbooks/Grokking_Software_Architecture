package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.tests;

import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.domain.PortfolioManager;    
import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.infrastructure.adapters.FakePriceProvider;

/**
 * ARCHITECTURAL TEST
 */
public class PortfolioTests {

    private PortfolioTests() {
        // Private constructor to hide the implicit public one
    }

    public static void run() throws Exception {
        System.out.println("--- RUNNING ARCHITECTURAL TEST: HEXAGONAL ---");
        
        // Arrange
        FakePriceProvider fakeAdapter = new FakePriceProvider(50000.0);
        PortfolioManager manager = new PortfolioManager(fakeAdapter);

        // Act
        System.out.println("Test Action: Calculating value of 2 BTC at fixed $50,000 price...");
        double value = manager.calculateTotalValue(2.0);

        // Assert
        if (value == 100000.0) {
            System.out.println("SUCCESS: The portfolio correctly calculated $100,000. Test is stable!");
        } else {
            System.out.println("FAIL: Math error in Core logic.");
        }
    }
}
