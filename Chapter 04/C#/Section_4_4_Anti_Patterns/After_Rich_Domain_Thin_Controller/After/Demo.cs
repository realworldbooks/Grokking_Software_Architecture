using System;
using System.Diagnostics;
using System.IO;

namespace Chapter04.Section_4_4.After
{
    /* * ARCHITECTURAL NOTE: 
     * This 'After' state represents the Traditional 4-Layer Architecture.
     * The Presentation layer (Web API) acts as the Composition Root, 
     * wiring together Application, Domain, and Infrastructure.
     */
    public class Demo
    {
        public static void Run()
        {
            Console.WriteLine("--- Launching 'Rich Domain / Thin Controller' (After Refactoring) ---");
            Console.WriteLine("Starting the Presentation Layer and opening Swagger UI...");

            // This points to the entry point project of your 4-layer solution
            string projectPath = Path.GetFullPath("../Section_4_4_Anti_patterns/After_Rich_Domain_Thin_Controller/After/After.Presentation/After.Presentation.csproj");

            try
            {
                Process.Start(new ProcessStartInfo
                {
                    FileName = "dotnet",
                    // This triggers the 'http' profile in your launchSettings.json (Port 7200)
                    Arguments = $"run --project \"{projectPath}\" --launch-profile \"http\"",
                    UseShellExecute = true, 
                    CreateNoWindow = false
                });

                Console.WriteLine("\nSUCCESS: API is booting up at http://localhost:7200/swagger");
                Console.WriteLine("Notice how the Controller is now thin, delegating to the Application Layer.");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"ERROR: Could not launch the Presentation layer. {ex.Message}");
            }
            
            Console.WriteLine("--------------------------------------------------");
        }
    }
}