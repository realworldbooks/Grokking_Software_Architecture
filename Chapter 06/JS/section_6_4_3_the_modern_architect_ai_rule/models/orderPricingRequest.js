/**
 * The payload required to price a user's cart.
 */
class OrderPricingRequest {
    /**
     * The list of items in the user's shopping cart. 
     * Extract these from the user's conversation history.
     * @type {Array<Object>}
     * @example [ { "productId": "WIDGET-99", "quantity": 2 }, { "productId": "DIGITAL-EBOOK-01", "quantity": 1 } ]
     */
    items;

    /**
     * The destination zip code. 
     * AI INSTRUCTION: Must be exactly 5 digits. If the user provided a 9-digit zip, strip the last 4.
     * @type {string}
     * @example "12211"
     */
    zipCode;
}

module.exports = OrderPricingRequest;