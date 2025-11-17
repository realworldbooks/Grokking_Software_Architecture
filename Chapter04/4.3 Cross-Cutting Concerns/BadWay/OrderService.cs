namespace BadWay
{
    public class OrderService
    {
        public void SaveOrder(Order order)
        {
            // This is a hidden, rigid dependency.
            StaticFileLogger.Log("Saving order...");
            Console.WriteLine("(BAD_SERVICE) Order saved.");
        }
    }
}