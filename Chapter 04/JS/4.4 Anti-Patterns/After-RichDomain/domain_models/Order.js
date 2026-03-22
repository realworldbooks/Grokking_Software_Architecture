// Chapter 04/JS/4.4 Anti-Patterns/After-RichDomain/domain_models/Order.js

/**
 * Represents a customer's order.
 *
 * This is a "Rich Domain Model". It encapsulates its own
 * state (private fields using #) and handles its own business
 * rules (e.g., price validation, calculating discounts). This
 * solves the "Anemic Domain Model" anti-pattern where domain
 * objects are just bags of getters and setters.
 */
class Order {
    #customer;
    #items;
    static get GOLD_DISCOUNT_RATE() { return 0.9; } // 10% discount

    constructor(customer) {
        this.#customer = customer;
        this.#items = [];
        this.totalPrice = 0.0;
    }

    addItem(item, quantity) {
        if (quantity <= 0) {
            throw new Error("Quantity must be positive.");
        }
        
        // The Order class handles its own business logic.
        // It calculates the price, applying a discount if needed.
        let price = item.price * quantity;
        if (this.#customer.isGoldMember) {
            price *= Order.GOLD_DISCOUNT_RATE;
        }

        this.#items.push({
            item: item,
            quantity: quantity,
            price: price
        });
        this.#updateTotalPrice();
    }

    #updateTotalPrice() {
        this.totalPrice = this.#items.reduce((sum, i) => sum + i.price, 0);
    }

    getItems() {
        // Return a copy to prevent external modification
        return [...this.#items];
    }

    getCustomer() {
        return this.#customer;
    }
}

module.exports = Order;
