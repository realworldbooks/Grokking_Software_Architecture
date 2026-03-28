using System.Reflection;
using System.Text.Json;
namespace Chapter03;

// This class perfectly maps to the JSON structure
public class ExampleConfig
{
    public string Name { get; set; } = string.Empty;
    public string Type { get; set; } = string.Empty;
}

class Program
{
    static async Task Main()
    {
        // BULLETPROOF CHECK: Does the config file exist?
        if (!File.Exists("examples.json"))
        {
            Console.WriteLine("[ERROR] examples.json not found!");
            Console.WriteLine("Make sure it is set to 'Copy to Output Directory' in your project.");
            return;
        }

        string jsonString = await File.ReadAllTextAsync("examples.json");
        var options = new JsonSerializerOptions { PropertyNameCaseInsensitive = true };
        var examples = JsonSerializer.Deserialize<Dictionary<string, ExampleConfig>>(jsonString, options);

        while (true)
        {
            Console.Clear();
            Console.WriteLine("=== Grokking Software Architecture Chapter 03: C# Examples ===\n");

            // C# Dictionaries don't naturally keep their order, so we sort them by their numeric keys
            var orderedExamples = examples.OrderBy(x => int.Parse(x.Key));

            foreach (var kvp in orderedExamples)
            {
                Console.WriteLine($"{kvp.Key}. {kvp.Value.Name}");
            }

            Console.WriteLine("\nType 'exit' to quit.");
            Console.Write("\nEnter your choice: ");
            var choice = Console.ReadLine()?.Trim();

            if (choice?.ToLower() == "exit") break;

            if (examples.TryGetValue(choice, out var selectedExample))
            {
                Console.Clear();

                // ARCHITECTURAL NOTE: Reflection finds the exact class string at runtime
                Type type = Type.GetType(selectedExample.Type);

                if (type == null)
                {
                    Console.WriteLine($"[ERROR] Could not find class: {selectedExample.Type}");
                    Console.WriteLine("Check your namespace and class name in examples.json.");
                }
                else
                {
                    // Find the public, static Run() method inside that class
                    MethodInfo method = type.GetMethod("Run", BindingFlags.Public | BindingFlags.Static);

                    if (method == null)
                    {
                        Console.WriteLine($"[ERROR] Could not find a public static Run() method on {selectedExample.Type}");
                    }
                    else
                    {
                        Console.WriteLine($"--- Running {selectedExample.Name} ---\n");

                        // Execute the method!
                        var result = method.Invoke(null, null);
                        if (result is Task task) await task;
                    }
                }

                Console.WriteLine("\nPress any key to return to the main menu...");
                Console.ReadKey();
            }
            else
            {
                Console.WriteLine("Invalid choice. Please try again.");
                Console.WriteLine("\nPress any key to continue...");
                Console.ReadKey();
            }
        }
    }
}