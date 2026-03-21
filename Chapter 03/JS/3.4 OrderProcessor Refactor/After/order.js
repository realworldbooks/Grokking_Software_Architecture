/**
 * A Simple Data Transfer Object (DTO).
 * * ARCHITECTURE NOTE: This class is a pure data container. Its only 
 * responsibility is to hold the state of an order as it moves through 
 * the system.
 * * By keeping this class "thin" and free of business logic, we ensure 
 * that it remains a stable contract that all our specialized services 
 * (Validator, Payment, Inventory) can agree upon. The problem in our 
 * "Before" state wasn't this data—it was the monolithic way we 
 * processed it.
 */
class Order {
    /**
     * @param {string[]} items - List of item names.
     * @param {number} total - Total cost of the order.
     * @param {string} customerEmail - The destination for notifications.
     */
    constructor(items, total, customerEmail) {
        this.items = items;
        this.total = total;
        this.customerEmail = customerEmail;
    }
}

module.exports = Order;