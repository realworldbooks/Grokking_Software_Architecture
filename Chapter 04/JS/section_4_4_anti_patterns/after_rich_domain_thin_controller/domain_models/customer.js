// Chapter 04/JavaScript/4.4 Anti-Patterns/After-RichDomain/domain_models/customer.js

/**
 * ARCHITECTURE NOTE: Not every domain model needs complex behavior. 
 * Because the core business rules for this bounded context revolve 
 * around the Order, this Customer class can remain a simple data 
 * entity holding state.
 */
class Customer {
    constructor() {
        this.id = 0;
        this.type = ""; // e.g., "Gold"
        this.email = "";
    }
}

module.exports = Customer;