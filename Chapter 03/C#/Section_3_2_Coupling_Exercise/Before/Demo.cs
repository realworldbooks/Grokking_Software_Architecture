using System;

namespace Chapter03.CouplingTest.Before;

public static class Demo{
    public static void Run()
    {
        Console.WriteLine("--- Coupling Example (Before: High Coupling) ---");
        Console.WriteLine("The report generator has to make multiple, 'chatty' calls to the service.");

        var generator = new UserReportGenerator();
        var result = generator.GenerateReport(1);

        Console.WriteLine($"\\n  >> REPORT: {result}");
        Console.WriteLine("--------------------------------------------------\\n");
    }
}