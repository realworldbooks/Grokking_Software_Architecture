using After.DomainModels;

namespace After.DataAccess
{
    /// <summary>
    /// ARCHITECTURE NOTE: By isolating SQL logic here, we prevent 
    /// database concerns from "leaking" into the Presentation or 
    /// Business layers.
    /// </summary>
    // Concrete implementation for a SQL database (simulated)
   public class SqlOrderRepository : IOrderRepository
    {
        public Order GetById(int orderId) { return null; }
        public void Save(Order order) { /* SQL Logic */ }
    }

    public class SqlCustomerRepository : ICustomerRepository
    {
        public Customer GetById(int customerId)
        {
            return new Customer { 
                Id = customerId, Type = "Gold", Email = "a@b.com" 
            };
        }
    }
}