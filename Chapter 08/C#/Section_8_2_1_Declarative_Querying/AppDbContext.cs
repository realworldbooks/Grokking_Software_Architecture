// Section2_DeclarativeQuerying/AppDbContext.cs
using Microsoft.EntityFrameworkCore;

namespace Chapter08.DeclarativeQuerying
{
    /// <summary>
    /// THE DATABASE CONTEXT (Infrastructure Layer):
    /// 
    /// TEACHING NOTE:
    /// This is the bridge between our C# code and the actual database engine.
    /// By keeping this completely separate from our Models, we respect the 
    /// Separation of Concerns. If we ever swap EF Core for a different ORM 
    /// (like Dapper), our User model never has to change!
    /// </summary>
    public class AppDbContext : DbContext
    {
        // This DbSet represents the actual 'Users' table in the database
        public DbSet<User> Users { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            // We use a local file so the database persists just long enough for our demo
            optionsBuilder.UseSqlite("Data Source=orm_demo.db");
        }
    }
}