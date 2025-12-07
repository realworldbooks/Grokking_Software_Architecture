using After.Core;

namespace After.Application
{
    // DTO (Data Transfer Object) for the request
    public class OrderRequest
    {
        public int CustomerId { get; set; }
        public List<Item> Items { get; set; }
    }
}