using System;
using System.Collections.Generic;

namespace Chapter02.Testability;

// Fake DB for our test simulation
public class FakeDatabaseConnection : IDatabaseConnection
{
    public List<string> GetData(string query)
    {
        return new List<string> { "row1", "row2", "row3" };
    }
}

class Program
{
    static void Main()
    {
        Console.WriteLine("=== Chapter 2: Testability Example ===");

        Console.WriteLine("\n--- Running Before: Tightly Coupled Test ---");
        var generatorBefore = new ReportGeneratorBefore();
        var resultBefore = generatorBefore.Generate("FailingTest");
        var expectedBefore = "Report 'FailingTest' generated with 3 rows.";
        
        if (resultBefore != expectedBefore)
        {
            Console.WriteLine("❌ TEST FAILED!");
            Console.WriteLine($"  Expected: \"{expectedBefore}\"");
            Console.WriteLine($"  Received: \"{resultBefore}\"");
        }

        Console.WriteLine("\n--- Running After: Loosely Coupled Test ---");
        var fakeDb = new FakeDatabaseConnection();
        var generatorAfter = new ReportGeneratorAfter(fakeDb);
        
        var resultAfter = generatorAfter.Generate("PassingTest");
        var expectedAfter = "Report 'PassingTest' generated with 3 rows.";
        
        if (resultAfter == expectedAfter)
        {
            Console.WriteLine($"✅ TEST PASSED! Received expected result: \"{resultAfter}\"");
        }
        Console.WriteLine("\n======================================");
    }
}