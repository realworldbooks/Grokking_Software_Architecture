using System;

namespace GoodWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("--- Running 'Good Way' (Injected Logger) ---");
            // Dependencies are created and injected at the start
            ILogger logger = new FileLogger();
            var goodService = new OrderService(logger);
            goodService.SaveOrder(new Order());
            Console.WriteLine("--------------------------------------------");
        }
    }
}