/**
 * This file demonstrates the concept of maintainability by refactoring a single, complex function 
 * into smaller, more manageable and reusable functions.
 */

// --- BEFORE REFACTOR ---

/**
 * Processes a shopping cart order in a single, hard-to-maintain function.
 * 
 * @param {Array<Object>} cartItems - A list of items in the cart, where each item has a 'price' property.
 * @returns {string} A string summarizing the final total.
 */
function processOrderBefore(cartItems) {
    // 1. Calculating the subtotal.
    let subtotal = cartItems.reduce((sum, item) => sum + item.price, 0);

    // PROBLEM 1: "Magic Numbers"
    // The numbers 0.10 and 0.08 are "magic numbers." They are hardcoded values
    // without any explanation. If the discount or tax rate changes, a developer
    // has to hunt down these numbers in the code. In a large application, this
    // can be error-prone and time-consuming.
    const discount = subtotal * 0.10; // Magic number for discount rate
    const totalAfterDiscount = subtotal - discount;
    
    const tax = totalAfterDiscount * 0.08; // Magic number for tax rate
    const finalTotal = totalAfterDiscount + tax;
    
    // PROBLEM 2: Lack of Separation of Concerns
    // This function does everything: calculates subtotal, applies a discount, and adds tax.
    // If the logic for any of these steps changes, we have to modify this entire function.
    // This makes the function rigid and harder to test or reuse individual pieces of logic.
    return `Order processed! Your final total is $${finalTotal.toFixed(2)}`;
}

// --- AFTER REFACTOR ---

// IMPROVEMENT 1: Use Named Constants
// By defining the discount and tax rates as constants, we give them meaningful names.
// This makes the code self-documenting. If a rate needs to change, we only have to
// update it in one place, reducing the risk of errors.
const DISCOUNT_RATE = 0.10;
const TAX_RATE = 0.08;

/**
 * Calculates the subtotal of all items in the cart.
 * @param {Array<Object>} items - A list of items, each with a 'price' property.
 * @returns {number} The calculated subtotal.
 */
function calculateSubtotal(items) {
    // This function now has a single responsibility: calculating the subtotal.
    // It's easy to understand, test, and reuse.
    return items.reduce((sum, item) => sum + item.price, 0);
}

/**
 * Applies a discount to a given amount.
 * @param {number} amount - The original amount.
 * @param {number} rate - The discount rate to apply.
 * @returns {number} The amount after the discount is applied.
 */
function applyDiscount(amount, rate) {
    // This is another single-responsibility function. If the discount logic changes
    // (e.g., becomes a fixed amount instead of a percentage), we only need to change it here.
    return amount * (1 - rate);
}

/**
 * Adds tax to a given amount.
 * @param {number} amount - The original amount.
 * @param {number} rate - The tax rate to apply.
 * @returns {number} The amount after tax is added.
 */
function addTax(amount, rate) {
    // The tax calculation is also isolated. If tax rules change, this is the only
    // place that needs to be updated.
    return amount * (1 + rate);
}

/**
 * Processes the order using a more maintainable, modular approach.
 * @param {Array<Object>} cartItems - A list of items in the cart.
 * @returns {string} A string summarizing the final total.
 */
function processOrderAfter(cartItems) {
    // IMPROVEMENT 2: Method Decomposition
    // The business logic is now broken down into small, well-named functions.
    // The `processOrderAfter` function reads like a high-level summary of the steps involved.
    // This makes the code much more readable and easier to follow for new developers.
    // Each smaller function can be tested independently, improving testability.
    const subtotal = calculateSubtotal(cartItems);
    const totalAfterDiscount = applyDiscount(subtotal, DISCOUNT_RATE);
    const finalTotal = addTax(totalAfterDiscount, TAX_RATE);

    return `Order processed! Your final total is $${finalTotal.toFixed(2)}`;
}

// Export the functions so index.js can use them
module.exports = {
    processOrderBefore,
    processOrderAfter
};