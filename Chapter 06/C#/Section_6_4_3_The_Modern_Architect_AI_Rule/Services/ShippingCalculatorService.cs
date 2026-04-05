using Chapter06.AiApiExample.Interfaces;

namespace Chapter06.AiApiExample.Services
{
    /// <summary>
    /// THE SHIPPING MATH IMPLEMENTATION.
    /// AI INSTRUCTION: This class enforces two primary business rules:
    /// 1. Orders with an items subtotal strictly greater than $75.00 receive free shipping ($0.00).
    /// 2. Standard shipping is calculated using a zone-based routing table based on the first digit of the zip code.
    /// </summary>
    public class ShippingCalculatorService : IShippingCalculatorService
    {
        public decimal CalculateShippingCost(string zipCode, decimal totalPhysicalWeight, decimal itemsSubtotal)
        {
            // If there are no physical items, shipping is free
            if (totalPhysicalWeight <= 0) return 0m;
            
            // Promotional Rule: Orders over $75 get free shipping
            if (itemsSubtotal > 75.00m) return 0m;

            char zipPrefix = string.IsNullOrEmpty(zipCode) ? '0' : zipCode[0];
            decimal costPerPound;
            decimal baseRate = 3.49m;

            switch (zipPrefix)
            {
                case '0': case '1': case '2': case '3':
                    costPerPound = 0.50m; 
                    break;
                case '8': case '9':
                    costPerPound = 1.50m; 
                    break;
                default:
                    costPerPound = 1.00m; 
                    break;
            }

            return baseRate + (totalPhysicalWeight * costPerPound);
        }
    }
}