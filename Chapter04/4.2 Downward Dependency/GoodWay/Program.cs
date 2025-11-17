using System;

namespace GoodWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("--- Running 'Good Way' (Downward Dependency) ---");
            IOrderRepository goodRepo = new SqlOrderRepository();
            var goodService = new OrderService(goodRepo);
            goodService.SaveOrder(new Order());
            Console.WriteLine("----------------------------------------------");
        }
    }
}