const CartItem = require('./cartItem');

/**
 * Manages shopping cart operations.
 * ARCHITECTURAL NOTE: This "After" class is now highly focused. 
 * Because the CartItem model was extracted to its own file, this file 
 * only contains the pure business logic and is much easier to read.
 */
class ShoppingCart {
    // IMPROVEMENT 1: Use Named Constants
    // These are static and private to this class.
    static #DISCOUNT_RATE = 0.10;
    static #TAX_RATE = 0.08;

    /**
     * Single responsibility: calculating the subtotal.
     * @param {CartItem[]} items 
     * @returns {number}
     */
    #calculateSubtotal(items) {
        return items.reduce((sum, item) => sum + item.price, 0);
    }

    /**
     * Single responsibility: applying discounts.
     * @param {number} amount 
     * @returns {number}
     */
    #applyDiscount(amount) {
        return amount * (1 - ShoppingCart.#DISCOUNT_RATE);
    }

    /**
     * Single responsibility: applying tax rules.
     * @param {number} amount 
     * @returns {number}
     */
    #addTax(amount) {
        return amount * (1 + ShoppingCart.#TAX_RATE);
    }

    /**
     * Processes the order for a list of cart items.
     * @param {CartItem[]} cartItems 
     * @returns {string}
     */
    processOrder(cartItems) {
        // IMPROVEMENT 2: Method Decomposition
        // This method now reads like a high-level summary of the business process.
        const subtotal = this.#calculateSubtotal(cartItems);
        const totalAfterDiscount = this.#applyDiscount(subtotal);
        const finalTotal = this.#addTax(totalAfterDiscount);

        return `Order processed! Your final total is $${finalTotal.toFixed(2)}`;
    }
}

module.exports = ShoppingCart;