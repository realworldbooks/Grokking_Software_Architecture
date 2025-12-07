using System;
using System.Collections.Generic;

namespace Refactor.Before
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("--- Running 'Before Refactoring' Order Processor ---");
            var order = new Order { Items = new List<string> { "item1" }, Total = 100, CustomerEmail = "a@b.com" };
            var processor = new OrderProcessor();
            string result = processor.Process(order);
            Console.WriteLine(result);
            Console.WriteLine("------------------------------------------");
        }
    }
}