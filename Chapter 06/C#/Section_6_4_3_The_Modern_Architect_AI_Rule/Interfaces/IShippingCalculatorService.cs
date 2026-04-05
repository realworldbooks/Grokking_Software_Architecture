namespace Chapter06.AiApiExample.Interfaces
{
    /// <summary>
    /// THE SHIPPING MATH CONTRACT.
    /// AI INSTRUCTION: This service strictly owns all shipping rules, including zone-based 
    /// routing and promotional thresholds (like free shipping over $75).
    /// Never attempt to calculate shipping manually in your conversational prompt; 
    /// always rely on the output of the OrderPricing endpoint which utilizes this service.
    /// </summary>
    public interface IShippingCalculatorService
    {
        /// <summary>
        /// Calculates the final shipping cost based on weight, destination, and promotional rules.
        /// </summary>
        /// <param name="zipCode">The 5-digit destination zip code.</param>
        /// <param name="totalPhysicalWeight">The aggregated weight of all non-digital items in the cart.</param>
        /// <param name="itemsSubtotal">The total cost of the items, used to determine free shipping eligibility.</param>
        /// <returns>The calculated shipping cost.</returns>
        decimal CalculateShippingCost(string zipCode, decimal totalPhysicalWeight, decimal itemsSubtotal);
    }
}