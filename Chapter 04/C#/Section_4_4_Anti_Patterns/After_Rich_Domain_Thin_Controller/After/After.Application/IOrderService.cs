
namespace After.Application
{
    // The Application layer only owns its own service contract
    public interface IOrderService
    {
        OrderResponse CreateOrder(OrderRequest request);
    }
}