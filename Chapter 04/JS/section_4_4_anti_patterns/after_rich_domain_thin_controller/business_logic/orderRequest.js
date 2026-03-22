// Chapter 04/JavaScript/4.4 Anti-Patterns/After-RichDomain/business_logic/orderRequest.js

/**
 * DTO (Data Transfer Object) for incoming requests.
 */
class OrderRequest {
    /**
     * @param {number} customerId
     * @param {Array<import('../domain_models/item')>} items
     */
    constructor(customerId, items) {
        this.customerId = customerId;
        this.items = items;
    }
}

module.exports = OrderRequest;