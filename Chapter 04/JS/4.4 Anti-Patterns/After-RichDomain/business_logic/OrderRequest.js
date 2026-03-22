// Chapter 04/JS/4.4 Anti-Patterns/After-RichDomain/business_logic/OrderRequest.js

/**
 * A Data Transfer Object (DTO) used to pass data from the
 * Presentation layer to the Business Logic layer.
 *
 * This is not a domain model. It's a simple, flat data
 * structure that carries information for creating an order.
 */
class OrderRequest {
    /**
     * @param {number} customerId
     * @param {Array<{itemId: number, quantity: number}>} items
     */
    constructor(customerId, items) {
        this.customerId = customerId;
        this.items = items;
    }
}

module.exports = OrderRequest;
