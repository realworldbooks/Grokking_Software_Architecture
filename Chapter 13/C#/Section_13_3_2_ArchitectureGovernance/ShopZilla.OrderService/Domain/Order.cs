namespace ShopZilla.OrderService.Domain
{
    /// <summary>
    /// The core Order entity. This is the heart of the domain model.
    ///
    /// ARCHITECTURAL RULE: This class lives in the Domain layer and must
    /// NEVER reference anything from the Infrastructure layer (no databases,
    /// no HTTP clients, no external services). The Domain layer is the
    /// protected core of the system - it contains pure business logic only.
    ///
    /// Our fitness function (Listing 13.1) enforces this rule automatically
    /// in the CI pipeline. If anyone adds a dependency from this class to
    /// the Infrastructure layer, the build fails immediately.
    /// </summary>
    public class Order
    {
        public Guid Id { get; private set; }
        public string CustomerName { get; private set; } = string.Empty;
        public decimal TotalAmount { get; private set; }
        public OrderStatus Status { get; private set; }
        public DateTime CreatedAt { get; private set; }

        public Order(string customerName, decimal totalAmount)
        {
            Id = Guid.NewGuid();
            CustomerName = customerName;
            TotalAmount = totalAmount;
            Status = OrderStatus.Pending;
            CreatedAt = DateTime.UtcNow;
        }

        /// <summary>
        /// Transitions the order to the Paid state.
        /// This is pure domain logic - no infrastructure involved.
        /// </summary>
        public void MarkAsPaid()
        {
            if (Status != OrderStatus.Pending)
                throw new InvalidOperationException("Only pending orders can be marked as paid.");
            Status = OrderStatus.Paid;
        }

        /// <summary>
        /// Transitions the order to the Shipped state.
        /// Again, pure domain logic with zero infrastructure dependencies.
        /// </summary>
        public void MarkAsShipped()
        {
            if (Status != OrderStatus.Paid)
                throw new InvalidOperationException("Only paid orders can be shipped.");
            Status = OrderStatus.Shipped;
        }
    }
}