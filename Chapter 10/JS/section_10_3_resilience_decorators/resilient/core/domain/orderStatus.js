/**
 * * @description
 * In Vanilla JS, we use a frozen object to simulate an Enum. 
 * This ensures that the system states are immutable and prevents 
 * "Magic String" contamination across the hexagon.
 */
export const OrderStatus = Object.freeze({
    PENDING_PAYMENT: "PENDING_PAYMENT",
    PAID: "PAID",
    FAILED: "FAILED"
});