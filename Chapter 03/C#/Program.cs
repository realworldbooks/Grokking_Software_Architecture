using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Chapter03.CouplingTest.Before;
using Chapter03.CouplingTest.After;
using Chapter03.SRP.Before;
using Chapter03.SRP.After;

namespace Chapter03;

class Program
{
    static async Task Main()
    {
        var examples = new Dictionary<string, Func<Task>>
        {
            { "Coupling (Before)", () => { CouplingTest.Before.Demo.Run(); return Task.CompletedTask; } },
            { "Coupling (After)", () => { CouplingTest.After.Demo.Run(); return Task.CompletedTask; } },
            { "SRP (Before)", () => { SRP.Before.Demo.Run(); return Task.CompletedTask; } },
            { "SRP (After)", () => { SRP.After.Demo.Run(); return Task.CompletedTask; } }
        };

        while (true)
        {
            Console.WriteLine("=== Please choose an example to run: ===\n");
            int i = 1;
            foreach (var example in examples)
            {
                Console.WriteLine($"{i++}. {example.Key}");
            }
            Console.WriteLine("\nType 'exit' to quit.");
            
            Console.Write("\nEnter your choice: ");
            var choice = Console.ReadLine();

            if (int.TryParse(choice, out int selection) && selection > 0 && selection <= examples.Count)
            {
                var exampleToRun = examples.Values.ElementAt(selection - 1);
                Console.Clear();
                await exampleToRun();
            }
            else if (choice?.ToLower() == "exit")
            {
                break;
            }
            else
            {
                Console.WriteLine("Invalid choice. Please try again.");
            }
        }
    }
}