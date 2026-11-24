class OrderResponse {
/**
 * THE RESPONSE DTO
 * ARCHITECTURE NOTE: Returns the atomic truth calculated by the domain model.
 */
    constructor(orderId = 0, totalPrice = 0.0, customerEmail = "") {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.customerEmail = customerEmail;
    }
}
    module.exports = OrderResponse;