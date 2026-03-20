// --- BEFORE REFACTOR ---
function processOrderBefore(cartItems) {
    let subtotal = cartItems.reduce((sum, item) => sum + item.price, 0);
    const discount = subtotal * 0.10;
    const totalAfterDiscount = subtotal - discount;
    const tax = totalAfterDiscount * 0.08;
    const finalTotal = totalAfterDiscount + tax;
    
    return `Order processed! Your final total is $${finalTotal.toFixed(2)}`;
}

// --- AFTER REFACTOR ---
const DISCOUNT_RATE = 0.10;
const TAX_RATE = 0.08;

function calculateSubtotal(items) {
    return items.reduce((sum, item) => sum + item.price, 0);
}

function applyDiscount(amount, rate) {
    return amount * (1 - rate);
}

function addTax(amount, rate) {
    return amount * (1 + rate);
}

function processOrderAfter(cartItems) {
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