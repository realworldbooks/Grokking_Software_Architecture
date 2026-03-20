using After.Application;
using After.Core;

namespace After.Infrastructure
{
    // Concrete implementation for a SQL database (simulated)
    public class SqlOrderRepository : IOrderRepository
    {
        public Order GetById(int orderId)
        {
            Console.WriteLine("(INFRA) Getting order from SQL DB...");
            return null; // Stubbed for example
        }
        public void Save(Order order)
        {
            Console.WriteLine("(INFRA) Saving order to SQL DB...");
        }
    }

    // Concrete implementation for a SQL database (simulated)
    public class SqlCustomerRepository : ICustomerRepository
    {
        public Customer GetById(int customerId)
        {
            Console.WriteLine("(INFRA) Getting customer from SQL DB...");
            // Simulate finding a Gold customer
            return new Customer { Id = customerId, Type = "Gold", Email = "archie@example.com" };
        }
    }
}