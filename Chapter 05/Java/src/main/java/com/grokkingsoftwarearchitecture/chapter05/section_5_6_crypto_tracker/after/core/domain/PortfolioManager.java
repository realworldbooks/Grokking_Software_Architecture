package com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.domain;

import com.grokkingsoftwarearchitecture.chapter05.section_5_6_crypto_tracker.after.core.ports.PriceProviderPort;

/**
 * CORE – Pure business logic.
 * No HTTP clients, no JSON parsing. This class is fully isolated.
 */
public class PortfolioManager {
    private final PriceProviderPort priceProvider;

    /**
     * Dependency Injection via Constructor. 
     */
    public PortfolioManager(PriceProviderPort priceProvider) {
        this.priceProvider = priceProvider;
    }

    public double calculateTotalValue(double btcAmount) throws Exception {
        // We just call the port. We don't care WHERE the price comes from.
        double currentPrice = priceProvider.getBitcoinPrice();

        // NEW: Log the data we just got from the "Outside World"
        System.out.println("[Core] Calculating value: " + btcAmount + " BTC @ $" + currentPrice + " per coin");

        return btcAmount * currentPrice;
    }
}