using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using MyProject.Before.Models;

namespace MyProject.Before.Controllers
{
    // ANTI-PATTERN: The Fat Controller
    // Violates SRP: Handles Auth, Validation, Business Logic, Data Access, and Email.
    [ApiController]
    [Route("api/[controller]")]
    public class OrderController : ControllerBase
    {
        [HttpPost]
        public IActionResult CreateOrder(OrderRequest request)
        {
            // 1. Validation Logic
            // (Should be in the Domain or a Validator)
            if (request.Items == null || !request.Items.Any())
            {
                return BadRequest("Order must have items.");
            }

            // 2. Core Business Logic (Calculating Total)
            // (Should be inside the Order domain model)
            decimal total = 0;
            foreach (var item in request.Items)
            {
                total += item.Price * item.Quantity;
            }

            // 3. More Business Logic (Applying Discount)
            // (Should be a Domain Policy or Strategy)
            if (request.CustomerType == "Gold")
            {
                total *= 0.9m; // 10% discount
            }

            // 4. Data Access Logic
            // (Should be in a Repository / Infrastructure Layer)
            // TIGHT COUPLING: We are instantiating the DB context directly
            using (var dbContext = new MyDbContext())
            {
                var order = new Order
                {
                    Total = total,
                    CustomerEmail = request.CustomerEmail,
                    Items = request.Items
                };

                dbContext.Orders.Add(order);
                dbContext.SaveChanges();

                // 5. External Service Logic
                // (Should be in an Infrastructure Adapter)
                // TIGHT COUPLING: We are instantiating the Email service directly
                var emailService = new SmtpEmailService();
                emailService.Send(request.CustomerEmail, "Order Confirmed!");

                return Ok(order.Id);
            }
        }
    }

    // --- DUMMY CLASSES TO MAKE THE EXAMPLE COMPILE ---

    public class OrderRequest
    {
        public int CustomerId { get; set; }
        public string CustomerEmail { get; set; }
        public string CustomerType { get; set; }
        public List<Item> Items { get; set; }
    }

    public class MyDbContext : IDisposable
    {
        public DbSet<Order> Orders { get; set; } = new DbSet<Order>();
        public void SaveChanges() { /* Save to DB */ }
        public void Dispose() { }
    }

    public class DbSet<T>
    {
        public void Add(T entity) { /* Add to internal list */ }
    }

    public class SmtpEmailService
    {
        public void Send(string email, string message) { /* Send fake email */ }
    }
}