using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text.Json;

namespace Chapter07
{
    /// <summary>
    /// The Execution Layer for Chapter 09.
    /// Reads the examples.json configuration and uses Reflection to dynamically 
    /// launch the selected architectural demonstration.
    /// </summary>
    public class Program
    {
        // Maps to the structure of our examples.json file
        public class ExampleConfig
        {
            public string Name { get; set; } = string.Empty; 
            public string Type { get; set; } = string.Empty;
        }

        public static void Main(string[] args)
        {
            string configPath = "Examples.json";

            // BULLETPROOF CHECK: Ensure the configuration exists
            if (!File.Exists(configPath))
            {
                Console.ForegroundColor = ConsoleColor.Red;
                Console.WriteLine($"[ERROR] Configuration file '{configPath}' not found in the root directory!");
                Console.ResetColor();
                return;
            }

            // Parse the JSON into a dictionary
            var jsonString = File.ReadAllText(configPath);
            var examples = JsonSerializer.Deserialize<Dictionary<string, ExampleConfig>>(
                jsonString, new JsonSerializerOptions { PropertyNameCaseInsensitive = true }
            ) ?? new Dictionary<string, ExampleConfig>();

            while (true)
            {
                Console.Clear();
                Console.WriteLine("=== Grokking Software Architecture Chapter 09: C# Examples ===\n");

                // Sort keys numerically for a clean, logical menu display
                var sortedKeys = examples.Keys.OrderBy(k => int.Parse(k)).ToList();

                foreach (var key in sortedKeys)
                {
                    Console.WriteLine($"{key}. {examples[key].Name}");
                }

                Console.WriteLine("\nType 'exit' to quit.");
                Console.Write("\nEnter your choice: ");
                
                string choice = Console.ReadLine()?.Trim().ToLower() ?? string.Empty;

                if (choice == "exit")
                {
                    break;
                }

                if (examples.ContainsKey(choice))
                {
                    var selected = examples[choice];
                    Console.Clear();
                    Console.WriteLine($"--- Running: {selected.Name} ---\n");

                    ExecuteDemo(selected.Type, args);

                    Console.WriteLine("\nPress Enter to return to the main menu...");
                    Console.ReadLine();
                }
                else
                {
                    Console.WriteLine("\nInvalid choice. Press Enter to try again...");
                    Console.ReadLine();
                }
            }
        }

        /// <summary>
        /// Uses Reflection to find the target class and execute its entry method.
        /// </summary>
        private static void ExecuteDemo(string fullyQualifiedTypeName, string[] args)
        {
            try
            {
                // Grab the current assembly
                Assembly assembly = Assembly.GetExecutingAssembly();
                
                // Find the exact class specified in the JSON
                Type? type = assembly.GetType(fullyQualifiedTypeName);

                if (type == null)
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.WriteLine($"[ERROR] Could not locate class '{fullyQualifiedTypeName}'. Check your namespaces.");
                    Console.ResetColor();
                    return;
                }

                // Look for either a "Run", "RunAsync", or "Main" static method
                MethodInfo? method = type.GetMethod("RunAsync", BindingFlags.Public | BindingFlags.Static)
                                 ?? type.GetMethod("Run", BindingFlags.Public | BindingFlags.Static) 
                                 ?? type.GetMethod("Main", BindingFlags.Public | BindingFlags.Static);

                if (method != null)
                {
                    // If the method expects string[] args, pass them. Otherwise, pass null.
                    var parameters = method.GetParameters();
                    if (parameters.Length == 1 && parameters[0].ParameterType == typeof(string[]))
                    {
                        method.Invoke(null, new object[] { args });
                    }
                    else
                    {
                        method.Invoke(null, null);
                    }
                }
                else
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.WriteLine($"[ERROR] Class '{fullyQualifiedTypeName}' does not contain a static 'Run()' or 'Main()' method.");
                    Console.ResetColor();
                }
            }
            catch (Exception ex)
            {
                Console.ForegroundColor = ConsoleColor.Red;
                Console.WriteLine($"[ERROR] Execution failed: {ex.InnerException?.Message ?? ex.Message}");
                Console.ResetColor();
            }
        }
    }
}