using System.Collections.Generic;

namespace MyProject.Before.Models
{
    // ANTI-PATTERN: Anemic Domain Model
    // Just a "data bag" with no logic and no protection.
    public class Order
    {
        public int Id { get; set; }
        
        // DANGER: Public setter allows anyone to corrupt the state
        // e.g., order.Total = -999;
        public decimal Total { get; set; } 
        
        public string CustomerEmail { get; set; }
        
        public List<Item> Items { get; set; }
    }

    // Supporting class for the example
    public class Item
    {
        public string Name { get; set; }
        public decimal Price { get; set; }
        public int Quantity { get; set; }
    }
}