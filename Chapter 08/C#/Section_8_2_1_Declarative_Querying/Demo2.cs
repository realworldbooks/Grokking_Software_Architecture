// Section2_DeclarativeQuerying/Demo2.cs
using System;
using System.Linq;
using Microsoft.EntityFrameworkCore;

namespace Chapter08.DeclarativeQuerying
{
    public class Demo2
    {
        public static void RunQueryComparison()
        {
            Console.WriteLine("\n=== Section 8.2.1: Declarative Querying (Raw SQL vs ORM) ===");
            Console.WriteLine("SCENARIO: The database contains 4 users. We need to find all active users over age 21, sorted alphabetically.");

            using var context = new AppDbContext();
            
            // 1. Setup: Reset the database and let the ORM build the tables automatically
            context.Database.EnsureDeleted();
            context.Database.EnsureCreated();

            // 2. Seed the database with test data
            context.Users.AddRange(
                new User { FirstName = "Alice", LastName = "Smith", Age = 25, IsActive = true },
                new User { FirstName = "Bob", LastName = "Jones", Age = 19, IsActive = true },      // Fails: Too young
                new User { FirstName = "Charlie", LastName = "Brown", Age = 30, IsActive = false }, // Fails: Inactive
                new User { FirstName = "Diana", LastName = "Prince", Age = 28, IsActive = true }
            );
            context.SaveChanges();
            Console.WriteLine("SETUP: 4 Users inserted into the database.\n");

            // --- THE OLD WAY (IMPERATIVE) ---
            Console.WriteLine("--- 1. The Old Way (Imperative / Raw SQL) ---");
            string rawSql = "SELECT * FROM Users WHERE Age > 21 AND IsActive = 1 ORDER BY LastName";
            Console.WriteLine($"  [Action] Executing Raw String: {rawSql}");
            
            // We force EF Core to execute a raw string
            var rawUsers = context.Users.FromSqlRaw(rawSql).ToList();
            
            var foundRaw = string.Join(", ", rawUsers.Select(u => $"{u.FirstName} {u.LastName}"));
            Console.WriteLine($"  [Result] Found: [{foundRaw}]");
            Console.WriteLine("  [Lesson] The burden is on you. If you mistyped 'IsActive' as 'Active' inside that string,");
            Console.WriteLine("           your code would compile perfectly, but crash in production.\n");

            // --- THE MODERN WAY (DECLARATIVE) ---
            Console.WriteLine("--- 2. The Modern Way (Declarative / ORM) ---");
            Console.WriteLine("  [Action] Building a query object using native C# LINQ syntax...");
            
            // This is Listing 8.4 from the textbook!
            var ormUsers = context.Users
                .Where(u => u.Age > 21 && u.IsActive)
                .OrderBy(u => u.LastName)
                .ToList();

            var foundOrm = string.Join(", ", ormUsers.Select(u => $"{u.FirstName} {u.LastName}"));
            Console.WriteLine($"  [Result] Found: [{foundOrm}]");
            Console.WriteLine("  [Lesson] The ORM translates your C# LINQ into SQL safely behind the scenes.");
            Console.WriteLine("           If you rename 'User.Age' to 'User.YearsOld', Visual Studio will instantly flag the error.");
        }
    }
}