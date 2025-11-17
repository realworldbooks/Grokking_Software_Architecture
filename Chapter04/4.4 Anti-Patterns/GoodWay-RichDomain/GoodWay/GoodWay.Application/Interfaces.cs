using GoodWay.Core;

namespace GoodWay.Application
{
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
    public interface IOrderService
    {
        int CreateOrder(OrderRequest request);
    }
}