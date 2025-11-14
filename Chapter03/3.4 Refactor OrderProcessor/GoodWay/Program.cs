using System;
using System.Collections.Generic;

namespace Refactor.GoodWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("--- Running 'Good Way' Order Processor ---");
            var order = new Order { Items = new List<string> { "item1" }, Total = 100, CustomerEmail = "a@b.com" };

            // Dependencies are created here (the "composition root")
            // and injected into the service.
            var service = new OrderService(
                new OrderValidator(),
                new PaymentService(),
                new InventoryManager(),
                new NotificationService()
            );

            string result = service.ProcessOrder(order);
            Console.WriteLine(result);
            Console.WriteLine("------------------------------------------");
        }
    }
}