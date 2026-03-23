/**
 * THE RICH DOMAIN MODEL
 * ARCHITECTURE NOTE: This solves the "Anemic Domain" anti-pattern.
 * In the "Before" state, the Controller calculated the total and
 * applied discounts. Now, the Order class is responsible for its 
 * own data integrity. 
 */
class Order {
    
    // Encapsulation: Using JS private fields (#) so external classes 
    // cannot arbitrarily change the total or the id.
    #id;
    #total;
    #customerEmail;
    #items;

    static get GOLD_DISCOUNT_RATE() { return 0.9; }

    constructor(customerEmail) {
        if (!customerEmail) {
            throw new Error("customerEmail is required");
        }
        
        this.#customerEmail = customerEmail;
        this.#id = Math.floor(Math.random() * 9000) + 1000;
        this.#total = 0.0;
        this.#items = [];
    }

    // Getters map perfectly to C#'s { get; private set; }
    get id() { return this.#id; }
    get total() { return this.#total; }
    get customerEmail() { return this.#customerEmail; }

    // Returning a shallow copy prevents external array mutation
    get items() { return [...this.#items]; }

    /**
     * Behavior is now co-located with the data it mutates.
     * @param {import('./item')} item 
     * @param {import('./customer')} customer 
     */
    addItem(item, customer) {
        // Business Rule: Prices must be positive
        if (item.price <= 0) {
            throw new Error("Item price must be positive.");
        }
        
        this.#items.push(item);
        this.#recalculateTotal(customer);
    }

    /**
     * The discount logic lives here! If another part of the system 
     * creates an Order, they get this logic automatically.
     * @param {import('./customer')} customer 
     */
    #recalculateTotal(customer) {
        console.log("(DOMAIN) Calculating total...");
        
        let sum = this.#items.reduce(
            (acc, curr) => acc + (curr.price * curr.quantity), 0
        );
        
        if (customer.type === "Gold") {
            console.log("(DOMAIN) Applying Gold discount.");
            sum *= Order.GOLD_DISCOUNT_RATE; // 10% discount logic
        }
        this.#total = sum;
    }
}

module.exports = Order;