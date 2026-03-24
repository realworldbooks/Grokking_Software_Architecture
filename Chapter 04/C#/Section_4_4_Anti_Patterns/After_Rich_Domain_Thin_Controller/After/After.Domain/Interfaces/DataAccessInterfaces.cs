using After.Domain.Models;

namespace After.Domain.Interfaces
{
    /// <summary>
    /// ARCHITECTURE NOTE: In a traditional Layered architecture, the 
    /// Data Access Layer defines the contracts for accessing data. 
    /// The Business Logic layer above will be forced to depend on 
    /// this layer to use these interfaces.
    /// </summary>
    public interface IOrderRepository
    {
        Order GetById(int orderId);
        void Save(Order order);
    }

    public interface ICustomerRepository
    {
        Customer GetById(int customerId);
    }

    public interface IEmailService
    {
        void Send(string to, string subject, string body);
    }

    public interface IItemRepository
    {
        // This is the "Security Hook" the Service uses to verify prices
        Item GetById(int itemId);
    }
}