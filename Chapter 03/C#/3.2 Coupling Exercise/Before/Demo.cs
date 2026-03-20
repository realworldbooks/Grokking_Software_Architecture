using System;

namespace Chapter03.CouplingTest.Before;

public static class Demo{
    public static void Run()
    {
        Console.WriteLine("=== Chapter 3: Coupling Test (BEFORE) ===");
        Console.WriteLine("Notice how many 'chatty' calls the client has to make!\n");

        var generator = new UserReportGenerator();
        var result = generator.GenerateReport(1);

        Console.WriteLine($"\nRESULT: {result}");
        Console.WriteLine("=========================================\n");
    }
}