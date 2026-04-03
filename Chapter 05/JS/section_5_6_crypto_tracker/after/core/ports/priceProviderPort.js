/**
 * PORT – Defines "What" we need (lives in Core).
 * Using a base class to act as the interface contract.
 */
class PriceProviderPort {
    async getBitcoinPrice() {
        throw new Error("Method 'getBitcoinPrice()' must be implemented by the Adapter.");
    }
}

module.exports = PriceProviderPort;