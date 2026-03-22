using System;

namespace Before
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("--- Running 'Before Refactoring' (Static Logger) ---");
            var beforeService = new OrderService();
            beforeService.SaveOrder(new Order());
            Console.WriteLine("-----------------------------------------");
        }
    }
}