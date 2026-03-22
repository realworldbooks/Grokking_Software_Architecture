// Chapter 04/JS/4.4 Anti-Patterns/After-RichDomain/domain_models/Customer.js

/**
 * Represents a customer.
 *
 * This is a simple data-carrying class with no logic,
 * representing an entity in our domain. In a real system,
 * this might have more complexity, but for this example, it's
 * just a container for customer information.
 */
class Customer {
    constructor(id, name, isGoldMember = false) {
        this.id = id;
        this.name = name;
        this.isGoldMember = isGoldMember;
    }
}

module.exports = Customer;
