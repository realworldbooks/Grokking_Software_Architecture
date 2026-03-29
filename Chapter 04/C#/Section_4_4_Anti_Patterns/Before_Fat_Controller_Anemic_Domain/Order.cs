using System.ComponentModel;
namespace Chapter04.Section_4_4.Before
{
    /// <summary>
    /// ARCHITECTURAL NOTE: The Anemic Domain Model.
    /// This class is merely a "data bag." It violates encapsulation by 
    /// exposing its internal state via public setters, allowing external 
    /// classes to bypass business rules.
    /// </summary>
    public class Order
    {
        /// <summary>
        /// Unique identifier for the order.
        /// </summary>
        public int Id { get; set; }
        
        /// <summary>
        /// DANGER: Public setter allows the Total to be set to any value 
        /// (e.g., negative numbers) from anywhere in the application.
        /// There is no "Defensible Decision" here.
        /// </summary>
        public decimal Total { get; set; } 
        
        /// <summary>
        /// The email address for the customer. No validation is performed 
        /// on the property itself.
        /// </summary>
        public string CustomerEmail { get; set; }
        
    }

 public class OrderRequest
    {

        [DefaultValue(1)]
        public int CustomerId { get; set; } = 1;
        /// <summary>
        /// A raw list of items. External callers can add or remove items 
        /// without the Order knowing, which makes recalculating the Total 
        /// a nightmare.
        /// </summary>
        public List<OrderItemRequest> Items { get; set; }  = new List<OrderItemRequest>
        {
            // This pre-fills the array in the Swagger UI JSON request
            new OrderItemRequest { ItemId = 1, Quantity = 3 }
        };
    }

    public class OrderItemRequest
    {
        [DefaultValue(1)]
        public int ItemId { get; set; } = 1;
        [DefaultValue(3)]
        public int Quantity { get; set; } = 3;
    }
}