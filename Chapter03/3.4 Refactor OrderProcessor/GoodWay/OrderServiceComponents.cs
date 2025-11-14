namespace Refactor.GoodWay
{
    // --- Step 1: The Individual Service Classes ---

    // Handles only validation logic
    public class OrderValidator
    {
        public void Validate(Order order)
        {
            Console.WriteLine("(GoodWay) Validating order...");
            if (order.Items.Count == 0 || order.Total <= 0)
            {
                throw new InvalidOperationException("Order is invalid.");
            }
        }
    }

    // Handles only payment processing
    public class PaymentService
    {
        public bool ProcessPayment(Order order)
        {
            Console.WriteLine($"(GoodWay) Processing payment for ${order.Total}...");
            // Real payment gateway logic would go here
            return true;
        }
    }

    // Handles only inventory updates
    public class InventoryManager
    {
        public void UpdateInventory(Order order)
        {
            Console.WriteLine("(GoodWay) Updating inventory...");
            // Real database logic to update stock would go here
        }
    }

    // Handles only sending notifications
    public class NotificationService
    {
        public void SendConfirmationEmail(Order order)
        {
            Console.WriteLine($"(GoodWay) Sending confirmation email to {order.CustomerEmail}...");
            // Real email sending logic would go here
        }
    }
}