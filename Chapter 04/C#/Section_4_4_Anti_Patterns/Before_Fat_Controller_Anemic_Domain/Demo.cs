using System;
using System.Diagnostics;
using System.IO;
using System.Linq;

namespace Chapter04.Section_4_4.Before
{
    public class Demo
    {
        public static void Run()
        {
            Console.WriteLine("--- Launching 'The Fat Controller / Anemic Domain' (Anti-Pattern) ---");
            
            // 1. Auto-discover the project file instead of hardcoding the path
            string currentDir = Directory.GetCurrentDirectory();
            var matchingFiles = Directory.GetFiles(currentDir, "Before.csproj", SearchOption.AllDirectories)
                                         .Where(p => p.Contains("Section_4_4"))
                                         .ToArray();

            if (matchingFiles.Length == 0)
            {
                Console.WriteLine($"\n[ERROR] Could not find the Before.csproj for Section 4.4 anywhere under {currentDir}");
                return;
            }

            string projectFile = matchingFiles[0];
            string projectFolder = Path.GetDirectoryName(projectFile)!;
            
            Console.WriteLine($"[DEBUG] Found project at: {projectFile}");

            // 2. Launch the Web API
            try
            {
                var process = new Process
                {
                    StartInfo = new ProcessStartInfo
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

                process.OutputDataReceived += (sender, args) => { if (args.Data != null) Console.WriteLine($"[API] {args.Data}"); };
                process.ErrorDataReceived += (sender, args) => { if (args.Data != null) Console.WriteLine($"[API ERROR] {args.Data}"); };

                process.Start();
                process.BeginOutputReadLine();
                process.BeginErrorReadLine();

                Console.WriteLine("\n[SUCCESS] API process started. Read the logs below.");
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