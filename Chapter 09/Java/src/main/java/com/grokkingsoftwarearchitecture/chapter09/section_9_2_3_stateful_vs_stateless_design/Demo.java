package com.grokking.chapter09.section_9_2_3_stateful_vs_stateless;

import com.grokking.chapter09.section_9_2_3_stateful_vs_stateless.services.UserService;
import com.grokking.chapter09.section_9_2_3_stateful_vs_stateless.infrastructure.LocalStorageProvider;
import com.grokking.chapter09.section_9_2_3_stateful_vs_stateless.infrastructure.SimulatedCloudStorageProvider;

import java.io.File;
import java.io.FileNotFoundException;

public class Demo {

    public static void runStatefulScenario() {
        System.out.println("\n=== Scenario 1: Stateful Design (The Fragile Monolith) ===");
        System.out.println("THE SETUP: Two web servers running behind a Load Balancer.");
        System.out.println("THE ARCHITECTURE: Using LocalStorageProvider (Stateful).\n");

        try {
            // 1. Setup: We simulate two separate servers, each with their own isolated hard drive.
            UserService serverAService = new UserService(new LocalStorageProvider("server_A"));
            UserService serverBService = new UserService(new LocalStorageProvider("server_B"));

            System.out.println("--- Request 1: User uploads a profile picture ---");
            System.out.println("  [Load Balancer] Routing traffic to Server A...");
            
            // The file gets saved physically onto Server A's disk.
            serverAService.uploadAvatar("user_123", "face_data_001");
            System.out.println("  [Result] Upload Successful (Saved to Server A's local drive).\n");

            System.out.println("--- Request 2: User refreshes to view their profile ---");
            System.out.println("  [Load Balancer] Server A is busy. Routing traffic to Server B...");
            
            // Server B attempts to read the file. It checks its own drive, but the file isn't there!
            serverBService.viewAvatar("user_123");
                
        } catch (FileNotFoundException e) {
            // This is the exact moment horizontal scaling breaks.
            System.out.println("\n  [Result] FATAL CRASH: FileNotFoundException!");
            System.out.println("  [Lesson] Stateful design breaks horizontal scaling. Server B has no idea");
            System.out.println("           what Server A did. The state is trapped on a single machine.\n");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            // Clean up our simulated local directories
            deleteDirectory(new File("server_A_drive"));
            deleteDirectory(new File("server_B_drive"));
        }
    }

    public static void runStatelessScenario() {
        System.out.println("\n=== Scenario 2: Stateless Design (Cloud Native) ===");
        System.out.println("THE SETUP: Two web servers running behind a Load Balancer.");
        System.out.println("THE ARCHITECTURE: Using SimulatedCloudStorageProvider (Stateless).\n");

        try {
            // 1. Setup: Both server instances now point to the exact same external infrastructure.
            // We have successfully separated the 'Compute' (servers) from the 'State' (storage).
            SimulatedCloudStorageProvider sharedS3 = new SimulatedCloudStorageProvider("grokking-app-bucket");
            UserService serverAService = new UserService(sharedS3);
            UserService serverBService = new UserService(sharedS3);

            System.out.println("--- Request 1: User uploads a profile picture ---");
            System.out.println("  [Load Balancer] Routing traffic to Server A...");
            
            // Server A processes the logic, but immediately hands the data off to the external cloud.
            serverAService.uploadAvatar("user_123", "face_data_001");
            System.out.println("  [Result] Upload Successful (Pushed to S3).\n");

            System.out.println("--- Request 2: User refreshes to view their profile ---");
            System.out.println("  [Load Balancer] Routing traffic to Server B...");
            
            // Server B fetches the data from the central cloud adapter. It doesn't matter that 
            // Server B wasn't the one who originally handled the upload!
            String data = serverBService.viewAvatar("user_123");
            
            System.out.println("  [Result] SUCCESS! Server B downloaded the file. Data: '" + data + "'");
            System.out.println("  [Lesson] Stateless servers are interchangeable. Any server can handle");
            System.out.println("           any request because the 'state' lives safely in the cloud.\n");
            
        } catch (Exception e) {
            System.out.println("\n  [Result] ERROR: " + e.getMessage());
        }
    }

    // Helper method to clean up local folders after the demo runs
    private static void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directoryToBeDeleted.delete();
    }
}