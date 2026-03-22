using After.DomainModels; // Assuming your domain models are still in Core

namespace After.Application
{
    // The Application layer only owns its own service contract
    public interface IOrderService
    {
        int CreateOrder(OrderRequest request);
    }
}