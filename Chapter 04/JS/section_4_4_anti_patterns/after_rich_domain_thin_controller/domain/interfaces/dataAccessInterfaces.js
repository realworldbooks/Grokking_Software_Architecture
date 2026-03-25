/**
 * ARCHITECTURE NOTE: In a traditional Layered Architecture, the 
 * Infrastructure Layer defines the contracts for accessing data. 
 * The Business Logic layer above will be forced to depend on 
 * this layer to use these interfaces.
 * 
 * * In JavaScript, we simulate interfaces by creating base classes 
 * that throw errors if their methods aren't overridden.
 */
class IOrderRepository {
    getById(orderId) { throw new Error("Not implemented"); }
    save(order) { throw new Error("Not implemented"); }
}

class ICustomerRepository {
    getById(customerId) { throw new Error("Not implemented"); }
}

class IEmailService {
    send(to, subject, body) { throw new Error("Not implemented"); }
}

class IItemRepository {
    /**
     * ARCHITECTURE NOTE: This is the "Security Hook" the Service uses 
     * to verify prices. Fetching the item from the database ensures 
     * we use the official price, not one sent in a request DTO. Defines the contract for data access.
     * The Application Layer depends on this interface, not a concrete DB.
     * * @param {number} itemId 
     * @returns {Item}
     */
    getById(itemId) {
        throw new Error("Method 'getById()' must be implemented.");
    }
}

module.exports = {
    IOrderRepository,
    ICustomerRepository,
    IEmailService,
    IItemRepository
};