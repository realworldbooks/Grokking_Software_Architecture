/**
 * THE CORE (Tightly Coupled).
 * This class is a major architectural liability because it mixes pure 
 * domain logic with network infrastructure concerns.
 */
class PortfolioManager {
    
    /**
     * Calculates the total USD value of a Bitcoin balance.
     * @param {number} btcAmount 
     * @returns {Promise<number>}
     */
    async calculateTotalValue(btcAmount) {
        // VIOLATION 1: Hard-coded infrastructure dependency.
        // Using native fetch means this code CANNOT run offline.
        const response = await fetch("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd", {
            headers: { "User-Agent": "JS App" }
        });

        if (!response.ok) {
            throw new Error("Network response was not ok");
        }

        // VIOLATION 2: The logic is tangled with a specific external JSON format.
        const priceData = await response.json();
        const currentPrice = priceData.bitcoin.usd;
        
        return btcAmount * currentPrice;
    }
}

module.exports = PortfolioManager;