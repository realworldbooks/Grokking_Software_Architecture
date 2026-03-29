namespace Chapter04.Section_4_3.Before
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