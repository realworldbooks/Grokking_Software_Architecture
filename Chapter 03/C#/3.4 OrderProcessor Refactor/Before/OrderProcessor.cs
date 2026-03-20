using System;

namespace Chapter03.OrderProcessorRefactor.Before;

public class OrderProcessor
{
    public string Process(Order order)
    {
        // 1. Validation
        Console.WriteLine("  [Validate] Validating order...");
        if (order.Items.Count == 0 || order.Total <= 0)
        {
            throw new InvalidOperationException("Order is invalid.");
        }

        // 2. Payment Processing
        Console.WriteLine($"  [Payment] Processing payment for ${order.Total}...");
        bool paymentSuccess = true; 

        // 3. Inventory Update & 4. Confirmation Email
        if (paymentSuccess)
        {
            Console.WriteLine("  [Inventory] Updating inventory...");
            Console.WriteLine($"  [Notify] Sending confirmation email to {order.CustomerEmail}...");
            return "Order processed successfully.";
        }
        else
        {
            return "Payment failed.";
        }
    }
}