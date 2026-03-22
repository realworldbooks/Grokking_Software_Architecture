// Chapter 04/JavaScript/4.4 Anti-Patterns/After-RichDomain/data_access/dataAccessInterfaces.js

/**
 * ARCHITECTURE NOTE: In a traditional Layered Architecture, the 
 * Data Access Layer defines the contracts for accessing data. 
 * The Business Logic layer above will be forced to depend on 
 * this layer to use these interfaces.
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

module.exports = {
    IOrderRepository,
    ICustomerRepository,
    IEmailService
};