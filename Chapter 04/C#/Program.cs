using System.Reflection;
using System.Text.Json;
using System.Linq;
using System.IO;

namespace Chapter04;

public class ExampleConfig
{
    public string Name { get; set; } = string.Empty;
    public string Type { get; set; } = string.Empty;
}

class Program
{
    static async Task Main()
    {
        // No 'using' or 'typeof' for sub-sections here!
        
        const string configPath = "Examples.json";

        if (!File.Exists(configPath))
        {
            Console.WriteLine($"[ERROR] {configPath} not found!");
            return;
        }

        string jsonString = await File.ReadAllTextAsync(configPath);
        var options = new JsonSerializerOptions { PropertyNameCaseInsensitive = true };
        var examples = JsonSerializer.Deserialize<Dictionary<string, ExampleConfig>>(jsonString, options);

        while (true)
        {
            Console.Clear();
            Console.WriteLine("=== Grokking Software Architecture Chapter 04: C# Examples ===\n");

            var orderedExamples = examples.OrderBy(x => int.Parse(x.Key));

            foreach (var kvp in orderedExamples)
            {
                Console.WriteLine($"{kvp.Key}. {kvp.Value.Name}");
            }

            Console.WriteLine("\nType 'exit' to quit.");
            Console.Write("\nEnter your choice: ");
            var choice = Console.ReadLine()?.Trim();

            if (choice?.ToLower() == "exit") break;

            if (examples != null && examples.TryGetValue(choice ?? "", out var selectedExample))
            {
                Console.Clear();
                await RunExample(selectedExample.Type);
                Console.WriteLine("\nPress any key to return to the main menu...");
                Console.ReadKey();
            }
        }
    }

  private static async Task RunExample(string typeName)
{
    try
    {
        var executionPath = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location);
        foreach (var dll in Directory.GetFiles(executionPath!, "*.dll"))
        {
            try { Assembly.LoadFrom(dll); } catch { }
        }

        // --- NEW DEBUG SECTION ---
        Console.WriteLine("--- Detailed Search ---");
        foreach (var assembly in AppDomain.CurrentDomain.GetAssemblies().Where(a => a.GetName().Name == "Before" || a.GetName().Name == "After"))
        {
            Console.WriteLine($"Scanning Assembly: {assembly.GetName().Name}");
            var types = assembly.GetTypes();
            foreach (var t in types)
            {
                Console.WriteLine($"  Found Class: '{t.FullName}'");
            }
        }
        // -------------------------

        Type? type = AppDomain.CurrentDomain.GetAssemblies()
            .SelectMany(a => a.GetTypes())
            .FirstOrDefault(t => t.FullName == typeName);

        if (type == null)
        {
            Console.WriteLine($"\n[ERROR] Could not find class: {typeName}");
            return;
        }

        var method = type.GetMethod("Run", BindingFlags.Public | BindingFlags.Static);
        if (method == null)
        {
            Console.WriteLine($"[ERROR] Missing public static Run() method on {typeName}");
            return;
        }

        var result = method.Invoke(null, null);
        if (result is Task task) await task;
    }
    catch (Exception ex)
    {
        Console.WriteLine($"[RUNTIME ERROR] {ex.InnerException?.Message ?? ex.Message}");
    }}
}