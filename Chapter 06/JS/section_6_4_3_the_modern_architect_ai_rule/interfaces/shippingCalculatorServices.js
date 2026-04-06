/**
 * THE SHIPPING MATH CONTRACT.
 * AI INSTRUCTION: This service strictly owns all shipping rules, including zone-based 
 * routing and promotional thresholds (like free shipping over $75).
 * Never attempt to calculate shipping manually in your conversational prompt.
 */
class ShippingCalculatorService {
    /**
     * @param {string} zipCode 
     * @param {number} totalPhysicalWeight 
     * @param {number} itemsSubtotal 
     */
    calculateShippingCost(zipCode, totalPhysicalWeight, itemsSubtotal) {
        throw new Error("Method 'calculateShippingCost' must be implemented.");
    }
}

module.exports = ShippingCalculatorService;