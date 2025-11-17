using System;

namespace BadWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("--- Running 'Bad Way' (Static Logger) ---");
            var badService = new OrderService();
            badService.SaveOrder(new Order());
            Console.WriteLine("-----------------------------------------");
        }
    }
}