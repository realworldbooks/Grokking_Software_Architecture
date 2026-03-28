using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using MyProject.Before.Models;

namespace MyProject.Before.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class OrderController : ControllerBase
    {
        /// <summary>
        /// ARCHITECTURAL NOTE: The "God Method" Transaction Script
        /// Because this method instantiates its own dependencies (using the 'new' keyword), 
        /// it is completely untestable in isolation. You cannot test the discount math 
        /// without also hitting the database and sending a real email.
        /// </summary>
        [HttpPost]
        public IActionResult CreateOrder(OrderRequest request)
        {
            // 1. Validation Logic
            if (request.Items == null || !request.Items.Any())
            {
                return BadRequest("Order must have items.");
            }

            // ARCHITECTURAL NOTE: Tight Coupling to Infrastructure
            // By directly instantiating MyDbContext, we cannot swap out the database 
            // for testing or future migrations.
            using (var dbContext = new MyDbContext())
            {
                // ARCHITECTURAL NOTE: Messy Inline Lookup
                // The controller is acting as a data repository.
                var customer = dbContext.Customers.FirstOrDefault(c => c.Id == request.CustomerId);
                if (customer == null)
                {
                    return BadRequest("Customer not found.");
                }

                decimal total = 0;

                // ARCHITECTURAL NOTE: The N+1 Query Problem & Leaked Business Logic
                // We are querying the database inside a loop, which destroys performance.
                // Furthermore, the controller is doing the math instead of the Domain!
                foreach (var reqItem in request.Items)
                {
                    var dbItem = dbContext.Items.FirstOrDefault(i => i.Id == reqItem.ItemId);
                    if (dbItem == null)
                    {
                        return BadRequest($"Item {reqItem.ItemId} not found.");
                    }

                    total += dbItem.Price * reqItem.Quantity;
                }

                // ARCHITECTURAL NOTE: Hardcoded Business Rules
                // If marketing decides "Gold" members now get a 15% discount, 
                // you have to modify, recompile, and redeploy the API controller.
                if (customer.Type == "Gold")
                {
                    total *= 0.9m; // 10% discount
                }

                // ARCHITECTURAL NOTE: The Anemic Domain Model
                // We just stuff the calculated data into a dumb property bag. 
                // The Order object has no idea how to calculate its own total.
                var order = new Order
                {
                    Id = new Random().Next(1000, 9999),
                    Total = total,
                    CustomerEmail = customer.Email
                };

                dbContext.Orders.Add(order);
                dbContext.SaveChanges();

                // ARCHITECTURAL NOTE: Hidden Side Effects
                // If the SMTP server is down, the order creation fails entirely, 
                // resulting in a terrible user experience.
                var emailService = new SmtpEmailService();
                emailService.Send(order.CustomerEmail, "Order Confirmed!");

                return Ok(new
                {
                    OrderId = order.Id,
                    TotalPrice = order.Total,
                    CustomerEmail = order.CustomerEmail
                });
            }
        }
    }
    // --- DUMMY CLASSES FOR COMPILATION ---
    // In a real 'Before' scenario, these would likely be scattered across the project 
    // or hidden in a massive 'Helpers' folder.



    /// <summary>
    /// ARCHITECTURAL NOTE: The Mock Database Context.
    /// This mimics a direct Entity Framework context, demonstrating how 
    /// persistence logic is often leaked directly into the UI/API layer.
    /// </summary>
    public class MyDbContext : IDisposable
    {
        public List<Customer> Customers { get; set; } = new List<Customer>
        {
            new Customer { Id = 1, Type = "Gold", Email = "a@b.com" }
        };

        public List<DbItem> Items { get; set; } = new List<DbItem>
        {
            new DbItem { Id = 1, Name = "Laptop", Price = 100.0m },
            new DbItem { Id = 2, Name = "Mouse", Price = 50.0m }
        };

        public List<Order> Orders { get; set; } = new List<Order>();
        public void SaveChanges() { }
        public void Dispose() { }
    }

    public class Customer { public int Id { get; set; } public string Type { get; set; } public string Email { get; set; } }
    public class DbItem { public int Id { get; set; } public string Name { get; set; } public decimal Price { get; set; } }
    public class SmtpEmailService { public void Send(string e, string m) { Console.WriteLine($"Email sent to {e}"); } }
}