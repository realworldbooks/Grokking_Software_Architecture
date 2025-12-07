using System;

namespace Before
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("--- Running 'Before Refactoring' (Upward Dependency) ---");
            var beforeRepo = new SomeRepository();
            beforeRepo.UpdateData(123, "New Data");
            Console.WriteLine("---------------------------------------------");
        }
    }
}