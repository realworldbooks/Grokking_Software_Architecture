// Program.cs
using System;
using Chapter08.DatabaseCode;         
using Chapter08.DeclarativeQuerying;   

namespace Chapter08
{
    class Program
    {
        static void Main(string[] args)
        {
            while (true)
            {
                Console.WriteLine("\n" + new string('=', 60));
                Console.WriteLine("=== Chapter 8: The Database as an Architectural Pillar ===");
                Console.WriteLine(new string('=', 60));
                
                Console.WriteLine("\n--- Section 8.1.4: SQL vs. NoSQL vs. Vector ---");
                Console.WriteLine("1. The Literal Search (The Naive Baseline)");
                Console.WriteLine("2. The Metadata Workaround (Columns & Tags)");
                Console.WriteLine("3. The 'Fat Finger' Test (Fuzzy Intent)");
                Console.WriteLine("4. The Schema Agility Test (Business Pivot)");
                Console.WriteLine("5. The Aggregation Test (Give Me The Math)");
                Console.WriteLine("6. The Hybrid Search (The Holy Grail)");

                Console.WriteLine("\n--- Section 8.2.1: Declarative Querying (ORMs) ---");
                Console.WriteLine("7. Run Query Comparison (Raw SQL vs. Entity Framework)\n");
                
                Console.WriteLine("\n0. Exit");
                Console.WriteLine(new string('=', 60));
                
                Console.Write("\nEnter your choice (0-7): ");
                var choice = Console.ReadLine()?.Trim();

                switch (choice)
                {
                    case "1": Demo.RunScenario0LiteralSearch(); break;
                    case "2": Demo.RunScenario1MetadataWorkaround(); break;
                    case "3": Demo.RunScenario2FatFinger(); break;
                    case "4": Demo.RunScenario3SchemaAgility(); break;
                    case "5": Demo.RunScenario4Aggregation(); break;
                    case "6": Demo.RunScenario5HybridSearch(); break;
                    
                    case "7": Demo2.RunQueryComparison(); break;
                    
                    case "0":
                        Console.WriteLine("Exiting Chapter 8 Demo...");
                        return;
                    default:
                        Console.WriteLine("Invalid choice. Please enter a number between 0 and 7.");
                        continue;
                }
                
                Console.WriteLine("\nPress Enter to return to the main menu...");
                Console.ReadLine();
            }
        }
    }
}