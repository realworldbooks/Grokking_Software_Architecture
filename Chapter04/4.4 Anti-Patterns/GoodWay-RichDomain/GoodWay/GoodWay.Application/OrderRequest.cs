using GoodWay.Core;

namespace GoodWay.Application
{
    // DTO (Data Transfer Object) for the request
    public class OrderRequest
    {
        public int CustomerId { get; set; }
        public List<Item> Items { get; set; }
    }
}