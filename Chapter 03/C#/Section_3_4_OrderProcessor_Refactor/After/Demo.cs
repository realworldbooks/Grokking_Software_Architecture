using System;
using System.Collections.Generic;

namespace Chapter03.OrderProcessorRefactor.After;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: Order Processor (AFTER) ===");
        Console.WriteLine("A coordinator class delegates to focused services...\n");

        var order = new Order 
        { 
            Items = new List<string> { "Book", "Pen" }, 
            Total = 25.50m, 
            CustomerEmail = "customer@example.com" 
        };

        // Dependency Injection in action!
        var service = new OrderService(
            new OrderValidator(),
            new PaymentService(),
            new InventoryManager(),
            new NotificationService()
        );

        var result = service.ProcessOrder(order);

        Console.WriteLine($"\nRESULT: {result}");
        Console.WriteLine("==========================================\n");
    }
}