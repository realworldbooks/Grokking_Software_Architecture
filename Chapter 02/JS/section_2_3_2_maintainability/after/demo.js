const ShoppingCart = require('./shoppingCart');
const CartItem = require('./cartItem');

/**
 * Demonstrates the optimized ShoppingCart logic.
 */
class Demo {
    static run() {
        console.log("--- Maintainability: Shopping Cart (AFTER) ---");
        console.log("Notice how easy it is to read the decomposed processOrder() method.\n");
        
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