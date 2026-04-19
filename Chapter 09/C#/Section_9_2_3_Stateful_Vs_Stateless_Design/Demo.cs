using System;
using System.IO;
using Chapter09.Section1_StatefulVsStateless.Services;
using Chapter09.Section1_StatefulVsStateless.Infrastructure;

namespace Chapter09.Section1_StatefulVsStateless;

public class Demo
{
    public static void RunStatefulScenario()
    {
        Console.WriteLine("\n=== Scenario 1: Stateful Design (The Fragile Monolith) ===");
        Console.WriteLine("THE SETUP: Two web servers running behind a Load Balancer.");
        Console.WriteLine("THE ARCHITECTURE: Using LocalStorageProvider (Stateful).\n");

        try
        {
            // 1. Setup: We simulate two separate servers, each with their own isolated hard drive.
            var serverAService = new UserService(new LocalStorageProvider("ServerA"));
            var serverBService = new UserService(new LocalStorageProvider("ServerB"));

            Console.WriteLine("--- Request 1: User uploads a profile picture ---");
            Console.WriteLine("  [Load Balancer] Routing traffic to Server A...");
            
            // The file gets saved physically onto Server A's disk.
            serverAService.UploadAvatar("user_123", "face_data_001");
            Console.WriteLine("  [Result] Upload Successful (Saved to Server A's local drive).\n");

            Console.WriteLine("--- Request 2: User refreshes to view their profile ---");
            Console.WriteLine("  [Load Balancer] Server A is busy. Routing traffic to Server B...");
            
            // Server B attempts to read the file. It checks its own C: drive, but the file isn't there!
            serverBService.ViewAvatar("user_123");
        }
        catch (FileNotFoundException)
        {
            // This is the exact moment horizontal scaling breaks.
            Console.WriteLine("\n  [Result] FATAL CRASH: FileNotFoundException!");
            Console.WriteLine("  [Lesson] Stateful design breaks horizontal scaling. Server B has no idea");
            Console.WriteLine("           what Server A did. The state is trapped on a single machine.\n");
        }
        finally
        {
            // Clean up our simulated local directories
            if (Directory.Exists("ServerA_drive")) Directory.Delete("ServerA_drive", true);
            if (Directory.Exists("ServerB_drive")) Directory.Delete("ServerB_drive", true);
        }
    }

    public static void RunStatelessScenario()
    {
        Console.WriteLine("\n=== Scenario 2: Stateless Design (Cloud Native) ===");
        Console.WriteLine("THE SETUP: Two web servers running behind a Load Balancer.");
        Console.WriteLine("THE ARCHITECTURE: Using SimulatedCloudStorageProvider (Stateless).\n");

        // 1. Setup: Both server instances now point to the exact same external infrastructure.
        // We have successfully separated the 'Compute' (servers) from the 'State' (storage).
        var sharedCloudStorage = new SimulatedCloudStorageProvider("grokking-app-bucket");
        var serverAService = new UserService(sharedCloudStorage);
        var serverBService = new UserService(sharedCloudStorage);

        Console.WriteLine("--- Request 1: User uploads a profile picture ---");
        Console.WriteLine("  [Load Balancer] Routing traffic to Server A...");
        
        // Server A processes the logic, but immediately hands the data off to the external cloud.
        serverAService.UploadAvatar("user_123", "face_data_001");
        Console.WriteLine("  [Result] Upload Successful (Pushed to Cloud Storage).\n");

        Console.WriteLine("--- Request 2: User refreshes to view their profile ---");
        Console.WriteLine("  [Load Balancer] Routing traffic to Server B...");
        
        // Server B fetches the data from the central cloud adapter. It doesn't matter that 
        // Server B wasn't the one who originally handled the upload!
        string data = serverBService.ViewAvatar("user_123");
        
        Console.WriteLine($"  [Result] SUCCESS! Server B downloaded the file. Data: '{data}'");
        Console.WriteLine("  [Lesson] Stateless servers are interchangeable. Any server can handle");
        Console.WriteLine("           any request because the 'state' lives safely in the cloud.\n");
    }
}