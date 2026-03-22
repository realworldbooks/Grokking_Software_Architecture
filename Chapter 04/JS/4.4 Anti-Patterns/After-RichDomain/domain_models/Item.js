// Chapter 04/JS/4.4 Anti-Patterns/After-RichDomain/domain_models/Item.js

/**
 * Represents an item that can be added to an order.
 *
 * Like Customer, this is a simple data class. It holds
 * information about a product but doesn't contain any
 * business logic itself.
 */
class Item {
    constructor(id, name, price) {
        if (price <= 0) {
            throw new Error("Price must be positive.");
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

module.exports = Item;
