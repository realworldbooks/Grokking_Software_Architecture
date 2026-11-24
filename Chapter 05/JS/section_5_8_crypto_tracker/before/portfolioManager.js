class PortfolioManager {
    constructor() {
        this.bitcoinCount = 0;
    }

    addBitcoin(amount) {
        this.bitcoinCount += amount;
    }

    async calculateTotalValue() {
        console.log("Fetching live price from CoinGecko...");
        try {
            // ARCHITECTURAL VIOLATION: Business logic knows about HTTP and URLs
            const response = await fetch('https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd');
            
            if (!response.ok) {
                // If the API rate limits us, we catch it here instead of crashing Node
                console.log(`[WARNING] API Rate Limit hit (Status: ${response.status}). Falling back to cached price.`);
                return this.bitcoinCount * 65000.00; // Fake fallback price
            }

            const data = await response.json();
            return this.bitcoinCount * data.bitcoin.usd;

        } catch (error) {
            // Because the Domain is mixed with Infrastructure, 
            // network errors pollute the business logic.
            console.log(`[CRITICAL ERROR] Network failed: ${error.message}. Falling back to cached price.`);
            return this.bitcoinCount * 65000.00; // Fake fallback price
        }
    }
}

module.exports = PortfolioManager;