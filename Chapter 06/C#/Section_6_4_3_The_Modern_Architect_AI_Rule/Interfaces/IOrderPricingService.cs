using Chapter06.AiApiExample.Models;

namespace Chapter06.AiApiExample.Interfaces
{
    /// <summary>
    /// THE SERVICE CONTRACT.
    /// Completely isolated from the implementation to enforce strict Dependency Inversion.
    /// </summary>
    public interface IOrderPricingService
    {
        /// <summary>
        /// Calculates the item subtotals, physical shipping weight, and final costs
        /// based on business rules (like free shipping thresholds and zip code zones).
        /// </summary>
        /// <param name="request">The shopping cart and destination zip code.</param>
        /// <returns>The fully calculated order totals.</returns>
        OrderPricingResponse CalculateOrderTotals(OrderPricingRequest request);
    }
}