using System.Collections.Generic;
using Chapter06.AiApiExample.Domain;
using Chapter06.AiApiExample.Interfaces;

namespace Chapter06.AiApiExample.Repositories
{
    /// <summary>
    /// THE REPOSITORY.
    /// Handles all data access. This isolates the "database" from the business logic.
    /// </summary>
    public class ProductRepository : IProductRepository
    {
        // The dictionary lives here now!
        private readonly Dictionary<string, Product> _productDatabase = new()
        {
            { "WIDGET-99", new Product { Id = "WIDGET-99", Name = "Standard Widget", IsDigital = false, WeightInLbs = 5.0m, Price = 19.99m } },
            { "WIDGET-HEAVY", new Product { Id = "WIDGET-HEAVY", Name = "Anvil", IsDigital = false, WeightInLbs = 50.0m, Price = 99.99m } },
            { "DIGITAL-EBOOK-01", new Product { Id = "DIGITAL-EBOOK-01", Name = "Architecture PDF", IsDigital = true, WeightInLbs = 0m, Price = 29.99m } }
        };

        public Product GetById(string productId)
        {
            _productDatabase.TryGetValue(productId, out var product);
            return product; // Returns null if not found
        }
    }
}