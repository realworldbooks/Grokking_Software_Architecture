namespace Chapter06.AiApiExample.Models
{
    public class CartItem
    {
        /// <summary>
        /// The unique ID of the product. 
        /// VALID VALUES: 'WIDGET-99', 'WIDGET-HEAVY', 'DIGITAL-EBOOK-01'.
        /// Do not send any other values.
        /// </summary>
        /// <example>WIDGET-99</example>
        public string ProductId { get; set; }

        /// <summary>
        /// How many of this item the user is buying. Must be 1 or greater.
        /// </summary>
        /// <example>1</example>
        public int Quantity { get; set; }
    }
}