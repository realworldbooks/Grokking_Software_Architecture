/**
 * ADAPTER: CoinGecko Implementation.
 * Bridges the Domain's PriceProvider port to the real-world API.
 */
class CoinGeckoAdapter {
    async getBitcoinPrice() {
        try {
            const response = await fetch("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd");
            
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }

            const data = await response.json();
            return data.bitcoin.usd;
        } catch (error) {
            console.error(`[Adapter] Failed to fetch price: ${error.message}`);
            // Fallback for the demo if the internet is down
            return 65000; 
        }
    }
}

// FIX: Export the class so the Composition Root (Demo.js) can instantiate it!
module.exports = CoinGeckoAdapter;