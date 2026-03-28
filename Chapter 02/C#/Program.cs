namespace Chapter02;

class Program
{
    static async Task Main()
    {
        var examples = new Dictionary<string, Func<Task>>
        {
            { "Maintainability: Shopping Cart", () => { ShoppingCart.ShoppingCartDemo.Run(); return Task.CompletedTask; } },
            { "Testability: Dependency Injection", () => { TestabilityDemo.Run(); return Task.CompletedTask; } },
            { "Performance: Caching", () => { PerformanceDemo.Run(); return Task.CompletedTask; } },
            { "Constraints in Action", ConstraintsDemo.Run },
            { "Weighted Decision Model", () => { WeightedDecisionModelDemo.Run(); return Task.CompletedTask; } }
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