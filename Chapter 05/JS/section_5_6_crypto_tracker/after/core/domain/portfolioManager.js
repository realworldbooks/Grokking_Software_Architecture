/**
 * CORE – Pure business logic.
 * No fetch calls, no JSON parsing. This class is fully isolated.
 */
class PortfolioManager {
    /**
     * Dependency Injection via Constructor.
     * @param {Object} priceProvider - An adapter that implements getBitcoinPrice()
     */
    constructor(priceProvider) {
        this.priceProvider = priceProvider;
    }

    async calculateTotalValue(btcAmount) {
        // We just call the port. We don't care WHERE the price comes from.
        const currentPrice = await this.priceProvider.getBitcoinPrice();
        return btcAmount * currentPrice;
    }
}

module.exports = PortfolioManager;