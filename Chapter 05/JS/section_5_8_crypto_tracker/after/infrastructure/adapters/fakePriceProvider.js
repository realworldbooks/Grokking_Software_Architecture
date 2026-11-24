const PriceProviderPort = require('../../core/ports/priceProviderPort');

/**
 * ADAPTER 1: The "Airplane Mode" / Test Adapter.
 */
class FakePriceProvider extends PriceProviderPort {
    constructor(fixedPrice = 50000) {
        super();
        this.fixedPrice = fixedPrice;
    }

    async getBitcoinPrice() {
        return this.fixedPrice;
    }
}

module.exports = FakePriceProvider;