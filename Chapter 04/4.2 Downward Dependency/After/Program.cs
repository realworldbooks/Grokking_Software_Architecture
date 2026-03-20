using System;

namespace After
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("--- Running 'After Refactoring' (Downward Dependency) ---");
            IOrderRepository afterRepo = new SqlOrderRepository();
            var afterService = new OrderService(afterRepo);
            afterService.SaveOrder(new Order());
            Console.WriteLine("----------------------------------------------");
        }
    }
}