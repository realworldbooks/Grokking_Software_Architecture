using System;

namespace BadWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("--- Running 'Bad Way' (Upward Dependency) ---");
            var badRepo = new SomeRepository();
            badRepo.UpdateData(123, "New Data");
            Console.WriteLine("---------------------------------------------");
        }
    }
}