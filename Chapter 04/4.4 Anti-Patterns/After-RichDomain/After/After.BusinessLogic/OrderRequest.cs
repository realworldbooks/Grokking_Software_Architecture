using After.DomainModels;

namespace After.BusinessLogic
{
    // DTO (Data Transfer Object) for incoming requests    
    public class OrderRequest
    {
        public int CustomerId { get; set; }
        public List<Item> Items { get; set; }
    }

    // The Business Layer owns its own interface
    public interface IOrderService
    {
        int CreateOrder(OrderRequest request);
    }
}