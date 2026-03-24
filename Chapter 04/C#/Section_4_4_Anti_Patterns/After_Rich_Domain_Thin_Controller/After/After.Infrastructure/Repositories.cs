using After.Domain.Interfaces;
using After.Domain.Models;

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
            return new Customer
            {
                Id = customerId,
                Type = "Gold",
                Email = "a@b.com"
            };
        }
    }

    public class SqlItemRepository : IItemRepository
    {
        public Item GetById(int itemId)
        {
            // In a real app, this would be a SQL query: 
            // SELECT price FROM items WHERE id = ?
            Item item = new Item();

            if (item.Id == 1)
            {
                item.Price = 100.0m;
            }
            else if (item.Id == 2)
            {
                item.Price = 50.0m;
            }
            else
            {
                // Default or fallback for testing
                item.Price = 75.0m;
            }

            return item;
        }
    }
}