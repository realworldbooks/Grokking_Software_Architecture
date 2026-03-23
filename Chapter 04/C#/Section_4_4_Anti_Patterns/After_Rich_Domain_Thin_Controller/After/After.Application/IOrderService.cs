using After.DomainModels; // Assuming your domain models are still in Core

namespace After.BusinessLogic
{
    // The Application layer only owns its own service contract
    public interface IOrderService
    {
        int CreateOrder(OrderRequest request);
    }
}