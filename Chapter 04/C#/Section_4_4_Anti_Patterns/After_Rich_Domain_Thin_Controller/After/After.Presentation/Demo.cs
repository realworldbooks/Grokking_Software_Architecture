using System;
using System.Diagnostics;
using System.IO;
using System.Linq;

namespace Chapter04.Section_4_4.After
{
    public class Demo
    {
        public static void Run()
        {
            Console.WriteLine("--- Launching Clean Architecture (After) ---");
            
            string currentDir = Directory.GetCurrentDirectory();
            
            // Look specifically for the Presentation project
            var matchingFiles = Directory.GetFiles(currentDir, "After.Presentation.csproj", SearchOption.AllDirectories)
                                         .Where(p => p.Contains("Section_4_4"))
                                         .ToArray();

            if (matchingFiles.Length == 0)
            {
                Console.WriteLine($"\n[ERROR] Could not find the After.Presentation.csproj anywhere under {currentDir}");
                return;
            }

            string projectFile = matchingFiles[0];
            string projectFolder = Path.GetDirectoryName(projectFile)!;
            
            Console.WriteLine($"[DEBUG] Found entry project at: {projectFile}");

            try
            {
                var process = new Process
                {
                    StartInfo = new ProcessStartInfo // Use 'dotnet run' to launch the API project
                    {
                        FileName = "dotnet",
                        Arguments = $"run --project \"{projectFile}\"", 
                        WorkingDirectory = projectFolder,
                        UseShellExecute = false, 
                        RedirectStandardOutput = true,
                        RedirectStandardError = true,
                        CreateNoWindow = true 
                    }
                };

                // Force Development environment so Swagger definitely loads
                process.StartInfo.EnvironmentVariables["ASPNETCORE_ENVIRONMENT"] = "Development";

                process.OutputDataReceived += (sender, args) => { if (args.Data != null) Console.WriteLine($"[API] {args.Data}"); };
                process.ErrorDataReceived += (sender, args) => { if (args.Data != null) Console.WriteLine($"[API ERROR] {args.Data}"); };

                process.Start();
                process.BeginOutputReadLine();
                process.BeginErrorReadLine();

                Console.WriteLine("\n[SUCCESS] Clean Architecture API started.");
                Console.WriteLine("Press ENTER to kill the API and return to the main menu...\n");
                
                Console.ReadLine(); 

                if (!process.HasExited)
                {
                    process.Kill();
                    Console.WriteLine("API shut down successfully.");
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"[CRITICAL ERROR] {ex.Message}");
            }
        }
    }
}