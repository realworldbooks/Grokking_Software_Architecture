/**
 * CORE – Pure business logic.
 * No fetch calls, no JSON parsing. This class is fully isolated.
 */
class PortfolioManager {
    /**
     * Dependency Injection via Constructor.
     * @param priceProvider - An adapter that satisfies the PriceProvider contract.
     */
    constructor(priceProvider) {
        this.priceProvider = priceProvider;
    }

    /**
     * Calculates total value by awaiting the external price data.
     * @param btcAmount - The quantity of Bitcoin to calculate.
     */
    async calculateTotalValue(btcAmount) {
        // We must await the result from the provider
        const currentPrice = await this.priceProvider.getBitcoinPrice();
        const totalValue = btcAmount * currentPrice;

        const usd = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' });

        // Now currentPrice will be a number, not a Promise object
        console.log(`[Core] Calculating: ${btcAmount} BTC x ${usd.format(currentPrice)}/BTC`);
        
        return totalValue;
    }
}

module.exports = PortfolioManager;