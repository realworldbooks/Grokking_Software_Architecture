import { rmSync } from 'fs';
import { concatMap, catchError, finalize } from 'rxjs/operators';
import { EMPTY } from 'rxjs';
import { UserService } from './services/userService.js';
import { LocalStorageProvider } from './infrastructure/localStorageProvider.js';
import { SimulatedCloudStorageProvider } from './infrastructure/simulatedCloudStorageProvider.js';

export class Demo {
    
    static runStatefulScenario() {
        return new Promise((resolve) => {
            console.log("\n=== Scenario 1: Stateful Design (The Fragile Monolith) ===");
            console.log("THE SETUP: Two web servers running behind a Load Balancer.");
            console.log("THE ARCHITECTURE: Using LocalStorageProvider (Stateful).\n");

            // 1. Setup: We simulate two separate servers, each with their own isolated hard drive.
            const serverAService = new UserService(new LocalStorageProvider("server_A"));
            const serverBService = new UserService(new LocalStorageProvider("server_B"));

            console.log("--- Request 1: User uploads a profile picture ---");
            console.log("  [Load Balancer] Routing traffic to Server A...");

            // TEACHING NOTE:
            // In Reactive Programming, we use `pipe` to string operations together.
            // `concatMap` is crucial here: it tells the stream to wait until the 
            // upload is fully complete before starting the `viewAvatar` stream.
            serverAService.uploadAvatar("user_123", "face_data_001").pipe(
                concatMap(() => {
                    console.log("  [Result] Upload Successful (Saved to Server A's local drive).\n");
                    
                    console.log("--- Request 2: User refreshes to view their profile ---");
                    console.log("  [Load Balancer] Server A is busy. Routing traffic to Server B...");
                    
                    // Start the second stream. Server B attempts to read the file. 
                    // It checks its own drive, but the file isn't there!
                    return serverBService.viewAvatar("user_123");
                }),
                catchError(error => {
                    // This is the exact moment horizontal scaling breaks.
                    console.log(`\n  [Result] FATAL CRASH: ${error.message}`);
                    console.log("  [Lesson] Stateful design breaks horizontal scaling. Server B has no idea");
                    console.log("           what Server A did. The state is trapped on a single machine.\n");
                    return EMPTY; 
                }),
                finalize(() => {
                    // Clean up our simulated local directories
                    rmSync("server_A_drive", { recursive: true, force: true });
                    rmSync("server_B_drive", { recursive: true, force: true });
                    resolve(); // Releases the CLI menu back to the user
                })
            ).subscribe({
                next: (data) => {
                    // This won't execute in this scenario because it crashes first!
                    console.log(`  [Result] SUCCESS! Data: '${data}'`);
                }
            });
        });
    }

    static runStatelessScenario() {
        return new Promise((resolve) => {
            console.log("\n=== Scenario 2: Stateless Design (Cloud Native) ===");
            console.log("THE SETUP: Two web servers running behind a Load Balancer.");
            console.log("THE ARCHITECTURE: Using SimulatedCloudStorageProvider (Stateless).\n");

            // 1. Setup: Both server instances now point to the exact same external infrastructure.
            // We have successfully separated the 'Compute' (servers) from the 'State' (storage).
            const sharedS3 = new SimulatedCloudStorageProvider("grokking-app-bucket");
            const serverAService = new UserService(sharedS3);
            const serverBService = new UserService(sharedS3);

            console.log("--- Request 1: User uploads a profile picture ---");
            console.log("  [Load Balancer] Routing traffic to Server A...");

            // Server A processes the logic, but immediately hands the data off to the external cloud.
            serverAService.uploadAvatar("user_123", "face_data_001").pipe(
                concatMap(() => {
                    console.log("  [Result] Upload Successful (Pushed to S3).\n");
                    
                    console.log("--- Request 2: User refreshes to view their profile ---");
                    console.log("  [Load Balancer] Routing traffic to Server B...");
                    
                    // Server B fetches the data from the central cloud adapter. It doesn't matter that 
                    // Server B wasn't the one who originally handled the upload!
                    return serverBService.viewAvatar("user_123");
                }),
                catchError(error => {
                    console.log(`\n  [Result] ERROR: ${error.message}`);
                    return EMPTY;
                }),
                finalize(() => resolve())
            ).subscribe({
                next: (data) => {
                    console.log(`  [Result] SUCCESS! Server B downloaded the file. Data: '${data}'`);
                    console.log("  [Lesson] Stateless servers are interchangeable. Any server can handle");
                    console.log("           any request because the 'state' lives safely in the cloud.\n");
                }
            });
        });
    }
}