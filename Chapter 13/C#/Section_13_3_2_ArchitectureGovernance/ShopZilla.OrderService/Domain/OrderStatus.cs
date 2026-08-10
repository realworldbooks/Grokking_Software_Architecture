namespace ShopZilla.OrderService.Domain
{
    /// <summary>
    /// The lifecycle states of an Order.
    ///
    /// This enum lives in the Domain layer alongside the Order entity.
    /// It represents pure business vocabulary with no infrastructure
    /// dependencies whatsoever.
    /// </summary>
    public enum OrderStatus
    {
        /// <summary>Order has been created but not yet paid.</summary>
        Pending = 0,

        /// <summary>Payment has been received.</summary>
        Paid = 1,

        /// <summary>Order has been dispatched to the customer.</summary>
        Shipped = 2,

        /// <summary>Order was cancelled before shipping.</summary>
        Cancelled = 3
    }
}