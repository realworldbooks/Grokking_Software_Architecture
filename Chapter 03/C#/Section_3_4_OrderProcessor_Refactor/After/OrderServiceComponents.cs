using System;

namespace Chapter03.OrderProcessorRefactor.After;

// --- Step 1: The Individual Service Classes ---

/// <summary>
/// SRP SOLUTION: Domain Logic Isolation.
/// 
/// ARCHITECTURE NOTE: This class has one job: validation. It doesn't know 
/// about databases or payment gateways. This makes it incredibly easy to 
/// unit test our business rules without needing mock APIs or databases.
/// </summary>
public class OrderValidator
{
    public void Validate(Order order)
    {
        Console.WriteLine("  [Validate] Validating order...");
        if (order.Items.Count == 0 || order.Total <= 0)
        {
            throw new InvalidOperationException("Order is invalid.");
        }
    }
}

/// <summary>
/// SRP SOLUTION: External API Isolation.
/// 
/// ARCHITECTURE NOTE: If Stripe or PayPal changes their API, this is the 
/// ONLY file that needs to be updated. The rest of the checkout process 
/// remains completely untouched and safe.
/// </summary>
public class PaymentService
{
    public bool ProcessPayment(Order order)
    {
        Console.WriteLine($"  [Payment] Processing payment for ${order.Total}...");
        // Real payment gateway logic would go here
        return true;
    }
}

/// <summary>
/// SRP SOLUTION: Infrastructure Isolation.
/// 
/// ARCHITECTURE NOTE: If we migrate from SQL Server to PostgreSQL, the 
/// billing team and sales team won't even notice, because only this 
/// inventory manager class needs to change.
/// </summary>
public class InventoryManager
{
    public void UpdateInventory(Order order)
    {
        Console.WriteLine("  [Inventory] Updating inventory...");
        // Real database logic to update stock would go here
    }
}

/// <summary>
/// SRP SOLUTION: Communications Isolation.
/// 
/// ARCHITECTURE NOTE: Email formatting and delivery logic lives purely here.
/// If an email template fails to render, it will no longer crash the entire 
/// payment transaction!
/// </summary>
public class NotificationService
{
    public void SendConfirmationEmail(Order order)
    {
        Console.WriteLine($"  [Notify] Sending confirmation email to {order.CustomerEmail}...");
        // Real email sending logic would go here
    }
}