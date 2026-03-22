namespace After.DomainModels
{
    using System;
    using System.Collections.Generic;
    using System.Linq;

    /// <summary>
    /// THE RICH DOMAIN MODEL
    /// ARCHITECTURE NOTE: This solves the "Anemic Domain" anti-pattern.
    /// In the "Before" state, the Controller calculated the total and
    /// applied discounts. Now, the Order class is responsible for its 
    /// own data integrity. 
    /// </summary>
    public class Order
    {
        private const decimal GOLD_DISCOUNT_RATE = 0.9m;
        // Encapsulation: External classes cannot arbitrarily change 
        // the Total or the Id. They must use the provided methods.
        public int Id { get; private set; }
        public decimal Total { get; private set; }
        public string CustomerEmail { get; private set; }
        
        // Encapsulation: Prevents external code from doing _items.Add() 
        // which would bypass our RecalculateTotal logic.
        private readonly List<Item> _items = new List<Item>();
        public IReadOnlyList<Item> Items => _items.AsReadOnly();

        public Order(string customerEmail)
        {
            if (string.IsNullOrEmpty(customerEmail))
                throw new ArgumentNullException(nameof(customerEmail));
                
            CustomerEmail = customerEmail;
            Id = new Random().Next(1000, 9999); // Simulated ID
        }

        /// <summary>
        /// Behavior is now co-located with the data it mutates.
        /// </summary>
        public void AddItem(Item item, Customer customer)
        {
            // Business Rule: Prices must be positive
            if (item.Price <= 0)
            {
                throw new InvalidOperationException(
                    "Item price must be positive.");
            }
            
            _items.Add(item);
            RecalculateTotal(customer);
        }

        /// <summary>
        /// The discount logic lives here! If another part of the system 
        /// creates an Order, they get this logic automatically. No more 
        /// duplicated logic scattered across multiple controllers.
        /// </summary>
        private void RecalculateTotal(Customer customer)
        {
            Console.WriteLine("(DOMAIN) Calculating total...");
            var sum = _items.Sum(item => item.Price * item.Quantity);
            if (customer.Type == "Gold")
            {
                Console.WriteLine("(DOMAIN) Applying Gold discount.");
                sum*= GOLD_DISCOUNT_RATE; // 10% discount logic
            }
            Total = sum;
        }
    }
}