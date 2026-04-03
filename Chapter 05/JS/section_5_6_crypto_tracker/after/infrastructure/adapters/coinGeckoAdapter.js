const PriceProviderPort = require('../../core/ports/priceProviderPort');

/**
 * ADAPTER 2: The Real Production Adapter.
 * Encapsulates all the messy HTTP calls and 3rd-party JSON shapes here.
 */
class CoinGeckoAdapter extends PriceProviderPort {
    async getBitcoinPrice() {
        const response = await fetch("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd", {
            headers: { "User-Agent": "JS App" }
        });

        if (!response.ok) {
            throw new Error("Network response was not ok");
        }

        const priceData = await response.json();
        return priceData.bitcoin.usd;
    }
}

module.exports = CoinGeckoAdapter;