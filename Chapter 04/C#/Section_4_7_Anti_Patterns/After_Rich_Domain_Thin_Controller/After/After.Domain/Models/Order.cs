namespace After.Domain.Models
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Net;

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
        public Customer Customer { get; private set; }

        // Encapsulation: Prevents external code from doing _items.Add() 
        // which would bypass our RecalculateTotal logic.
        private readonly List<Item> _items = new List<Item>();
        public IReadOnlyList<Item> Items => _items.AsReadOnly();

        // Expression-bodied members for calculated data 
        public bool IsEligibleForDiscount => Customer != null && Customer.Type == "Gold";

        // Sometimes architects keep a separate CustomerEmail on the Order for Historical Accuracy.
        // The Scenario: If a customer changes their email address next year, do you want their old orders to show the new email or the email used at the time of purchase?
        // The Decision: If you want the order to be a "snapshot" in time, you store the string separately. If you want it to always reflect the customer's current email, you  use the alias.
        // This is an example of there being no "right" or "wrong" answer, simply a business preference.  We will use the alias here.
        public string CustomerEmail => Customer.Email;

        // The Atomic Truth: Logic and data are now perfectly unified.
        public decimal TotalPrice => _items.Sum(i => i.Price * i.Quantity)
                                 * (IsEligibleForDiscount ? GOLD_DISCOUNT_RATE : 1);

        /// <summary>
        /// Initializes a new instance of the <see cref="Order"/> class using a Rich Domain approach.
        /// </summary>
        /// <param name="customer">The <see cref="Customer"/> placing the order. 
        /// Required to encapsulate business rules like discount eligibility.</param>
        /// <exception cref="ArgumentNullException">Thrown when the customer is null, 
        /// ensuring the model's data integrity from the moment of creation.</exception>
        /// <remarks>
        /// ARCHITECTURE NOTE: By injecting the full Customer entity instead of just an email string, 
        /// the Order gains the "context" needed to calculate its own TotalPrice. 
        /// This eliminates the need for the Application Layer to manually handle business rules.
        /// </remarks>
        public Order(Customer customer)
        {
            // One assignment sets up the entire relationship
            Customer = customer ?? throw new ArgumentNullException(nameof(customer));
            Id = new Random().Next(1000, 9999);
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
        }
    }
}