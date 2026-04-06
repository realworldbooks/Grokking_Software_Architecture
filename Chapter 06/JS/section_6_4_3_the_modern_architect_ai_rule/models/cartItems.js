/**
 * Represents an individual item in the shopping cart.
 */
class CartItem {
    /**
     * The unique ID of the product. 
     * VALID VALUES: 'WIDGET-99', 'WIDGET-HEAVY', 'DIGITAL-EBOOK-01'.
     * Do not send any other values.
     * @type {string}
     * @example "WIDGET-99"
     */
    productId;

    /**
     * How many of this item the user is buying. Must be 1 or greater.
     * @type {number}
     * @example 1
     */
    quantity;
}

module.exports = CartItem;