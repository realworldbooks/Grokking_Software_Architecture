namespace Chapter06.AiApiExample.Models
{
    /// <summary>
    /// THE RESPONSE DTO.
    /// AI INSTRUCTION: This object contains the final, authoritative calculated totals.
    /// You must present these exact numbers to the user. Do not attempt to recalculate or verify them.
    /// </summary>
    public class OrderPricingResponse
    {
        /// <summary>
        /// The total cost of the physical and digital items before shipping is applied.
        /// </summary>
        /// <example>79.96</example>
        public decimal ItemsSubtotal { get; set; }

        /// <summary>
        /// The final calculated shipping cost. 
        /// AI INSTRUCTION: If this value is 0.00, inform the user that they qualified for free shipping.
        /// </summary>
        /// <example>0.00</example>
        public decimal ShippingCost { get; set; }

        /// <summary>
        /// The absolute final total the user will be charged.
        /// </summary>
        /// <example>79.96</example>
        public decimal TotalOrderCost { get; set; }
    }
}