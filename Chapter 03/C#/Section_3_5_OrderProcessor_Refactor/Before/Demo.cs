using System;
using System.Collections.Generic;

namespace Chapter03.OrderProcessorRefactor.Before;

public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: Order Processor (BEFORE) ===");
        Console.WriteLine("One massive class handles everything...\n");

        var order = new Order 
        { 
            Items = new List<string> { "Book", "Pen" }, 
            Total = 25.50m, 
            CustomerEmail = "customer@example.com" 
        };

        var processor = new OrderProcessor();
        var result = processor.Process(order);

        Console.WriteLine($"\nRESULT: {result}");
        Console.WriteLine("===========================================\n");
    }
}