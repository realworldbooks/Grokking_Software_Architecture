using Microsoft.EntityFrameworkCore;
using ShopZilla.OrderService.Domain;

namespace ShopZilla.OrderService.Infrastructure
{
    /// <summary>
    /// The repository that persists Order entities to the database.
    ///
    /// This is the Infrastructure layer's data access implementation.
    /// It depends on the Domain layer (Order entity) and the database
    /// context - both of which are "below" it in the dependency graph.
    ///
    /// ARCHITECTURAL RULE: The Domain layer must NEVER reference this
    /// class. Our fitness function enforces this boundary automatically.
    /// </summary>
    public class OrderRepository
    {
        private readonly OrderDbContext _dbContext;

        public OrderRepository(OrderDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        /// <summary>
        /// Persists a new order to the database.
        /// </summary>
        public async Task<Order> SaveAsync(Order order)
        {
            _dbContext.Orders.Add(order);
            await _dbContext.SaveChangesAsync();
            return order;
        }

        /// <summary>
        /// Retrieves an order by its unique identifier.
        /// </summary>
        public async Task<Order?> GetByIdAsync(Guid id)
        {
            return await _dbContext.Orders.FindAsync(id);
        }

        /// <summary>
        /// Retrieves all orders for a given customer.
        /// </summary>
        public async Task<List<Order>> GetByCustomerAsync(string customerName)
        {
            return await _dbContext.Orders
                .Where(o => o.CustomerName == customerName)
                .ToListAsync();
        }
    }
}