using System;

namespace Chapter03.OrderProcessorRefactor.Before;

/// <summary>
/// ANTI-PATTERN: The "God Class" / Monolithic Transaction Script.
/// 
/// ARCHITECTURE PROBLEM: This class is a massive violation of the Single 
/// Responsibility Principle (SRP) and is tightly coupled to four distinct domains.
/// 
/// If this were a real enterprise application, this one method would:
/// 1. Contain complex business validation rules.
/// 2. Talk to a third-party Payment Gateway API (like Stripe).
/// 3. Connect to a SQL Database to update inventory tables.
/// 4. Talk to a third-party Email API (like SendGrid).
/// 
/// WHY THIS FAILS:
/// - Testing: You cannot test the validation logic without accidentally triggering 
///   real payment calls or database updates.
/// - Maintenance: Four different teams (Billing, Inventory, Communications, Sales) 
///   will constantly be modifying this exact same file, causing merge conflicts.
/// - Brittleness: A timeout error from the Email API could cause the entire 
///   payment process to roll back or crash!
/// </summary>
public class OrderProcessor
{
    public string Process(Order order)
    {
        // 🚨 ARCHITECTURE WARNING: Domain Rule Violation
        // 1. Validation
        Console.WriteLine("  [Validate] Validating order...");
        if (order.Items.Count == 0 || order.Total <= 0)
        {
            throw new InvalidOperationException("Order is invalid.");
        }

        // 🚨 ARCHITECTURE WARNING: External Integration Coupling
        // 2. Payment Processing
        Console.WriteLine($"  [Payment] Processing payment for ${order.Total}...");
        bool paymentSuccess = true; 

        // 🚨 ARCHITECTURE WARNING: Infrastructure & Communications Coupling
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