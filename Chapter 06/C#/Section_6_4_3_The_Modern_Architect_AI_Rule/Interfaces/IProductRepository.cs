using Chapter06.AiApiExample.Domain;

namespace Chapter06.AiApiExample.Interfaces
{
    /// <summary>
    /// THE DATA ACCESS CONTRACT.
    /// AI INSTRUCTION: This is the absolute source of truth for the product catalog.
    /// Do not hallucinate or invent products. If a user requests an item that this 
    /// repository cannot find, you must inform them that the item does not exist.
    /// </summary>
    public interface IProductRepository
    {
        /// <summary>
        /// Retrieves a product by its unique identifier.
        /// </summary>
        /// <param name="productId">The exact ID of the product (e.g., 'WIDGET-99').</param>
        /// <returns>The Product entity, or null if it does not exist.</returns>
        Product GetById(string productId);
    }
}