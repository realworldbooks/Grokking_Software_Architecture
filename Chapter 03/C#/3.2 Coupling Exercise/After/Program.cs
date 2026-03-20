using System;

namespace Chapter03.CouplingTest.After;

class Program
{
    static void Main()
    {
        Console.WriteLine("=== Chapter 3: Coupling Test (AFTER) ===");
        Console.WriteLine("Notice how clean and 'chunky' the interaction is now!\n");

        var generator = new UserReportGenerator();
        var result = generator.GenerateReport(1);

        Console.WriteLine($"\nRESULT: {result}");
        Console.WriteLine("========================================\n");
    }
}