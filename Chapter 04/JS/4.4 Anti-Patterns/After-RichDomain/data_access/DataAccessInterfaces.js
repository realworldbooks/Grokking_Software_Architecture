// Chapter 04/JS/4.4 Anti-Patterns/After-RichDomain/data_access/DataAccessInterfaces.js

// In a traditional 4-layer architecture, the Data Access
// layer defines its own contracts. Higher layers will depend
// on these abstractions. JavaScript doesn't have native
// interfaces, so we use classes with JSDoc and runtime errors
// to simulate them.

/**
 * @interface
 */
class IOrderRepository {
    /**
     * @param {Order} order
     */
    saveOrder(order) {
        throw new Error("Not implemented");
    }
}

/**
 * @interface
 */
class IEmailService {
    /**
     * @param {Order} order
     */
    sendOrderConfirmation(order) {
        throw new Error("Not implemented");
    }
}

module.exports = { IOrderRepository, IEmailService };
