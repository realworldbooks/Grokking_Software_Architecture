using System;

namespace After
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("--- Running 'After Refactoring' (Injected Logger) ---");
            // Dependencies are created and injected at the start
            ILogger logger = new FileLogger();
            var afterService = new OrderService(logger);
            afterService.SaveOrder(new Order());
            Console.WriteLine("--------------------------------------------");
        }
    }
}