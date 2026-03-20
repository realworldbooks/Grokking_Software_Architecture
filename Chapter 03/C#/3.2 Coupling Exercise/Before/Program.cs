using System;

namespace Chapter03.CouplingTest.Before;

class Program
{
    static void Main()
    {
        Console.WriteLine("=== Chapter 3: Coupling Test (BEFORE) ===");
        Console.WriteLine("Notice how many 'chatty' calls the client has to make!\n");

        var generator = new UserReportGenerator();
        var result = generator.GenerateReport(1);

        Console.WriteLine($"\nRESULT: {result}");
        Console.WriteLine("=========================================\n");
    }
}