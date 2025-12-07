namespace Refactor.Before
{
    // The "monolithic" class that does everything
    public class OrderProcessor
    {
        public string Process(Order order)
        {
            // 1. Validation
            Console.WriteLine("(Before Refactoring) Validating order...");
            if (order.Items.Count == 0 || order.Total <= 0)
            {
                throw new InvalidOperationException("Order is invalid.");
            }

            // 2. Payment Processing
            Console.WriteLine($"(Before Refactoring) Processing payment for ${order.Total}...");
            bool paymentSuccess = true; // Simulating success

            // 3. Inventory Update & 4. Confirmation Email
            if (paymentSuccess)
            {
                Console.WriteLine("(Before Refactoring) Updating inventory...");
                Console.WriteLine($"(Before Refactoring) Sending confirmation email to {order.CustomerEmail}...");
                return "Order processed successfully.";
            }
            else
            {
                return "Payment failed.";
            }
        }
    }
}