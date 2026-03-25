/**
 * THE RICH DOMAIN MODEL
 * ARCHITECTURE NOTE: This solves the "Anemic Domain" anti-pattern.
 * In the "Before" state, the Controller calculated the total and
 * applied discounts. Now, the Order class is responsible for its 
 * own data integrity.
 */
class Order {
    #id;
    #customer; // Now stores the full Customer object, matching C#
    #items;

    static get GOLD_DISCOUNT_RATE() { return 0.9; }

   /**
     * @param {Object} customer - The Customer entity.
     * ARCHITECTURE NOTE: By injecting the full Customer entity instead of just 
     * an email string, the Order gains the "context" needed to calculate 
     * its own TotalPrice.
     */
    constructor(customer) {
        if (!customer) {
            throw new Error("customer is required");
        }
        
        this.#customer = customer;
        this.#id = Math.floor(Math.random() * 9000) + 1000;
        this.#items = [];
    }

    get id() { return this.#id; }
    
    // Expression-bodied equivalent for discount eligibility
    get isEligibleForDiscount() {
        return this.#customer && this.#customer.type === "Gold";
    }

    /** * The Atomic Truth: Logic and data are perfectly unified.
     * This replaces the manual 'recalculateTotal' method.
     */
    get totalPrice() {
        const sum = this.#items.reduce(
            (acc, curr) => acc + (curr.price * curr.quantity), 0
        );
        
        return this.isEligibleForDiscount 
            ? sum * Order.GOLD_DISCOUNT_RATE 
            : sum;
    }

    /** * Historical Accuracy vs. Alias: We use the alias approach here to 
     * always reflect the customer's current email.
     */
    get customerEmail() { 
        return this.#customer.email; 
    }

    // Returning a shallow copy prevents external array mutation
    get items() { 
        return Object.freeze([...this.#items]); 
    }

    /**
     * Behavior is now co-located with the data it mutates.
     * @param {import('./item')} item 
     */
    addItem(item) {
        // Business Rule: Prices must be positive
        if (item.price <= 0) {
            throw new Error("Item price must be positive.");
        }
        
        // No need to manually call recalculateTotal; 
        // the 'total' getter handles it dynamically.
        this.#items.push(item);
    }
}
module.exports = Order;