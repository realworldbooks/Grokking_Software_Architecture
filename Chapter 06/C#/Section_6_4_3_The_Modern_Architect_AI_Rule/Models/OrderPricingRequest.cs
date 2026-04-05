using System.Collections.Generic;

namespace Chapter06.AiApiExample.Models
{
    /// <summary>
    /// The payload required to price a user's cart.
    /// </summary>
    public class OrderPricingRequest 
    {
        /// <summary>
        /// The list of items in the user's shopping cart. 
        /// Extract these from the user's conversation history.
        /// </summary>
        /// <example>
        /// [
        ///   {
        ///     "productId": "WIDGET-99",
        ///     "quantity": 2
        ///   },
        ///   {
        ///     "productId": "DIGITAL-EBOOK-01",
        ///     "quantity": 1
        ///   }
        /// ]
        /// </example>
        public List<CartItem> Items { get; set; }

        /// <summary>
        /// The destination zip code. 
        /// AI INSTRUCTION: Must be exactly 5 digits. If the user provided a 9-digit zip, strip the last 4.
        /// </summary>
        /// <example>12211</example>
        public string ZipCode { get; set; }
    }
}