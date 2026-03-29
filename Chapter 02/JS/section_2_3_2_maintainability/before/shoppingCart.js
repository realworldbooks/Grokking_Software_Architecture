/**
 * ARCHITECTURAL NOTE: The "File Dump" Anti-Pattern
 * In messy codebases, developers often dump data models (like CartItem) 
 * into the exact same file as the business logic. As the app grows, 
 * this file will become thousands of lines long and impossible to navigate.
 */
class CartItem {
    constructor(name, price) {
        this.name = name;
        this.price = price;
    }
}

/**
 * Manages shopping cart operations.
 * ARCHITECTURAL NOTE: This "Before" class demonstrates poor maintainability 
 * due to magic numbers and a lack of Separation of Concerns.
 */
class ShoppingCart {
    /**
     * Processes the order for a list of cart items.
     * @param {CartItem[]} cartItems 
     * @returns {string}
     */
    processOrder(cartItems) {
        // 1. Calculating the subtotal.
        let subtotal = 0;
        for (const item of cartItems) {
            subtotal += item.price;
        }

        // PROBLEM 1: "Magic Numbers"
        // The numbers 0.10 and 0.08 are hardcoded values without any explanation.
        const discount = subtotal * 0.10; 
        const totalAfterDiscount = subtotal - discount;
        
        const tax = totalAfterDiscount * 0.08; 
        const finalTotal = totalAfterDiscount + tax;

        // PROBLEM 2: Lack of Separation of Concerns
        // This method does everything: calculates subtotal, applies discount, and adds tax.
        return `Order processed! Your final total is $${finalTotal.toFixed(2)}`;
    }
}

// We export the classes for use in the Demo
module.exports = { ShoppingCart, CartItem };