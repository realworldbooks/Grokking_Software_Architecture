const { ShoppingCart, CartItem } = require('./shoppingCart');

/**
 * Demonstrates the unoptimized ShoppingCart logic.
 */
class Demo {
    static run() {
        console.log("--- Maintainability: Shopping Cart (BEFORE) ---");
        console.log("Notice the 'magic numbers' and the rigid 'God Method' design.\n");
        
        const cart = [
            new CartItem("Laptop", 1000.00),
            new CartItem("Mouse", 50.00)
        ];

        const cartSystem = new ShoppingCart();
        console.log(cartSystem.processOrder(cart));
        console.log("\n-----------------------------------------");
    }
}

module.exports = Demo;