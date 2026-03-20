using System;
using System.Threading.Tasks;

namespace Chapter02.ConstraintsInAction;

class Program
{
    static async Task Main()
    {
        Console.WriteLine("=== Chapter 2: Constraints In Action Example ===\n");

        var controller = new ExportController();

        Console.WriteLine("[Simulating GET /export-user-data for User123]");
        await controller.ExportUserDataAsync("User123");

        Console.WriteLine("\n[Simulating GET /export-user-data for UnknownUser]");
        await controller.ExportUserDataAsync("UnknownUser");

        Console.WriteLine("\n==============================================");
    }
}