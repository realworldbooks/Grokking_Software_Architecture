using System;
using System.Collections.Generic;

namespace Refactor.BadWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("--- Running 'Bad Way' Order Processor ---");
            var order = new Order { Items = new List<string> { "item1" }, Total = 100, CustomerEmail = "a@b.com" };
            var processor = new OrderProcessor();
            string result = processor.Process(order);
            Console.WriteLine(result);
            Console.WriteLine("------------------------------------------");
        }
    }
}