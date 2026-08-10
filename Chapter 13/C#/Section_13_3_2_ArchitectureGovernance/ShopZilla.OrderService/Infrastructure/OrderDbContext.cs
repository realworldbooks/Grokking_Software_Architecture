using Microsoft.EntityFrameworkCore;
using ShopZilla.OrderService.Domain;

namespace ShopZilla.OrderService.Infrastructure
{
    /// <summary>
    /// The Entity Framework database context. This is the Infrastructure
    /// layer's gateway to the physical SQL database.
    ///
    /// ARCHITECTURAL RULE: The Infrastructure layer MAY depend on the
    /// Domain layer (it needs to persist Order entities), but the Domain
    /// layer must NEVER depend on this class. This is the "Downward
    /// Dependency Rule" from our Four-Layer Architecture blueprint.
    ///
    /// Our fitness function (Listing 13.1) enforces this rule. If any
    /// Domain class ever references this DbContext, the build fails.
    /// </summary>
    public class OrderDbContext : DbContext
    {
        public OrderDbContext(DbContextOptions<OrderDbContext> options)
            : base(options)
        {
        }

        /// <summary>The Orders table in the database.</summary>
        public DbSet<Order> Orders { get; set; } = null!;

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            // Configure the Order entity mapping
            modelBuilder.Entity<Order>(entity =>
            {
                entity.HasKey(o => o.Id);
                entity.Property(o => o.CustomerName).IsRequired().HasMaxLength(200);
                entity.Property(o => o.TotalAmount).HasPrecision(18, 2);
                entity.Property(o => o.Status).HasConversion<int>();
            });
        }
    }
}