namespace Refactor.BadWay
{
    // The "monolithic" class that does everything
    public class OrderProcessor
    {
        public string Process(Order order)
        {
            // 1. Validation
            Console.WriteLine("(BadWay) Validating order...");
            if (order.Items.Count == 0 || order.Total <= 0)
            {
                throw new InvalidOperationException("Order is invalid.");
            }

            // 2. Payment Processing
            Console.WriteLine($"(BadWay) Processing payment for ${order.Total}...");
            bool paymentSuccess = true; // Simulating success

            // 3. Inventory Update & 4. Confirmation Email
            if (paymentSuccess)
            {
                Console.WriteLine("(BadWay) Updating inventory...");
                Console.WriteLine($"(BadWay) Sending confirmation email to {order.CustomerEmail}...");
                return "Order processed successfully.";
            }
            else
            {
                return "Payment failed.";
            }
        }
    }
}