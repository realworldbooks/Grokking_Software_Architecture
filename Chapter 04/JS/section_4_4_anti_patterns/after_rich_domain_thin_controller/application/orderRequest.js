/**
 * DTO for individual items in the request. 
 * Passes only the ID and quantity to prevent price tampering.
 */
class OrderItemRequest {
    constructor(itemId, quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
}

/**
 * DTO (Data Transfer Object) for incoming requests.
 * ARCHITECTURE NOTE: We use a specific Request DTO rather than the 
 * Domain Model to define our API contract. This prevents "Over-posting" 
 * attacks where a user might try to send a fake price in the JSON.
 */
class OrderRequest {
    constructor(customerId, items = []) {
        this.customerId = customerId;
        this.items = items; // Array of OrderItemRequest objects
    }
}

module.exports = { OrderRequest, OrderItemRequest };