/**
 * THE SHIPPING MATH IMPLEMENTATION.
 * AI INSTRUCTION: This class enforces two primary business rules:
 * 1. Orders with an items subtotal strictly greater than $75.00 receive free shipping ($0.00).
 * 2. Standard shipping is calculated using a zone-based routing table based on the first digit of the zip code.
 */
class ShippingCalculatorService {
    /**
     * Calculates the final shipping cost based on weight, destination, and promotional rules.
     * * @param {string} zipCode - The 5-digit destination zip code.
     * @param {number} totalPhysicalWeight - The aggregated weight of all non-digital items.
     * @param {number} itemsSubtotal - The total cost of items for free shipping eligibility.
     * @returns {number} The calculated shipping cost.
     */
    calculateShippingCost(zipCode, totalPhysicalWeight, itemsSubtotal) {
        // If there are no physical items, shipping is free
        if (totalPhysicalWeight <= 0) return 0.0;
        
        // Promotional Rule: Orders over $75 get free shipping
        if (itemsSubtotal > 75.00) return 0.0;

        const zipPrefix = (!zipCode || zipCode.length === 0) ? '0' : zipCode[0];
        let costPerPound;
        const baseRate = 3.49;

        switch (zipPrefix) {
            case '0': case '1': case '2': case '3':
                costPerPound = 0.50; // East Coast
                break;
            case '8': case '9':
                costPerPound = 1.50; // West Coast
                break;
            default:
                costPerPound = 1.00; // Midwest/Standard
                break;
        }

        const totalCost = baseRate + (totalPhysicalWeight * costPerPound);
        return parseFloat(totalCost.toFixed(2));
    }
}

module.exports = ShippingCalculatorService;