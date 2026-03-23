// Chapter 04/CSharp/4.4-Rich-After/After.BusinessLogic/OrderRequest.cs
using System.Collections.Generic;

namespace After.BusinessLogic
{
    /// <summary>
    /// DTO for the items within a request.
    /// </summary>
    public class OrderItemRequest
    {
        public int ItemId { get; set; }
        public int Quantity { get; set; }
    }

    /// <summary>
    /// The main Request DTO.
    /// </summary>
    public class OrderRequest
    {
        public int CustomerId { get; set; }
        public List<OrderItemRequest> Items { get; set; }
    }
}