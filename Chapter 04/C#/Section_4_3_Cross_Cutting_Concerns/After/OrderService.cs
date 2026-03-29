namespace Chapter04.Section_4_3.After
{
    // 2. The Class "asks" for the dependency
    public class OrderService
    {
        private readonly ILogger _logger;

        // The dependency is "injected" via the constructor!
        public OrderService(ILogger logger)
        {
            _logger = logger;
        }

        public void SaveOrder(Order order)
        {
            // 3. Use the abstraction (follows DIP)
            _logger.Log("Saving order...");
            Console.WriteLine("(AFTER_SERVICE) Order saved.");
        }
    }
}