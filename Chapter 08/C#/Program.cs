// Program.cs
using System;

namespace Chapter08.DatabaseCode
{
    /// <summary>
    /// THE UI CONTROLLER (Separation of Concerns):
    /// By moving the interactive menu into its own file, we keep our architecture clean.
    /// This file handles the user experience, while Demo.cs handles the database logic.
    /// 
    /// Note: In C#, this entry point is conventionally named Program.cs.
    /// </summary>
    public class Program
    {
        public static void Main(string[] args)
        {
            while (true)
            {
                Console.WriteLine("\n" + new string('=', 60));
                Console.WriteLine("=== Chapter 8: SQL vs. NoSQL vs. Vector ===");
                Console.WriteLine(new string('=', 60));
                Console.WriteLine("0. The Literal Search (The Naive Baseline)");
                Console.WriteLine("1. The Metadata Workaround (Columns & Tags)");
                Console.WriteLine("2. The 'Fat Finger' Test (Fuzzy Intent)");
                Console.WriteLine("3. The Schema Agility Test (Business Pivot)");
                Console.WriteLine("4. The Aggregation Test (Give Me The Math)");
                Console.WriteLine("5. The Hybrid Search (The Holy Grail)");
                Console.WriteLine("6. Exit");
                Console.WriteLine(new string('=', 60));
                
                Console.Write("\nEnter your choice (0-6): ");
                var choice = Console.ReadLine()?.Trim();

                switch (choice)
                {
                    case "0":
                        Demo.RunScenario0LiteralSearch();
                        break;
                    case "1":
                        Demo.RunScenario1MetadataWorkaround();
                        break;
                    case "2":
                        Demo.RunScenario2FatFinger();
                        break;
                    case "3":
                        Demo.RunScenario3SchemaAgility();
                        break;
                    case "4":
                        Demo.RunScenario4Aggregation();
                        break;
                    case "5":
                        Demo.RunScenario5HybridSearch();
                        break;
                    case "6":
                        Console.WriteLine("Exiting Chapter 8 Demo...");
                        return; // Exits the while loop and the application
                    default:
                        Console.WriteLine("Invalid choice. Please enter a number between 0 and 6.");
                        continue;
                }
                
                Console.WriteLine("\nPress Enter to return to the main menu...");
                Console.ReadLine();
            }
        }
    }
}