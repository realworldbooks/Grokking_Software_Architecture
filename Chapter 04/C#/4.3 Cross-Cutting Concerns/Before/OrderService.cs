namespace Before
{
    public class OrderService
    {
        public void SaveOrder(Order order)
        {
            // This is a hidden, rigid dependency.
            StaticFileLogger.Log("Saving order...");
            Console.WriteLine("(BEFORE_SERVICE) Order saved.");
        }
    }
}