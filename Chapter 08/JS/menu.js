import * as readline from 'readline/promises';
import { stdin as input, stdout as output } from 'process';
import { Demo } from './section_8_1_4_database_comparison/demo.js';
import { Demo2 } from './section_8_2_1_declarative_querying/demo2.js';

class Chapter8Menu {
    static async display() {
        const rl = readline.createInterface({ input, output });

        while (true) {
            console.log("\n============================================================");
            console.log("=== Chapter 8: The Database as an Architectural Pillar ===");
            console.log("============================================================");
            
            console.log("\n--- Section 8.1.4: SQL vs. NoSQL vs. Vector ---");
            console.log("1. The Literal Search (The Naive Baseline)");
            console.log("2. The Metadata Workaround (Columns & Tags)");
            console.log("3. The 'Fat Finger' Test (Fuzzy Intent)");
            console.log("4. The Schema Agility Test (Business Pivot)");
            console.log("5. The Aggregation Test (Give Me The Math)");
            console.log("6. The Hybrid Search (The Holy Grail)");

            console.log("\n--- Section 8.2.1: Declarative Querying (ORMs) ---");
            console.log("7. Run Query Comparison (Raw SQL vs. Prisma)");
            
            console.log("\n0. Exit");
            console.log("============================================================");
            
            const choice = (await rl.question("\nEnter your choice (0-7): ")).trim();

            switch (choice) {
                case "1": Demo.runScenario0LiteralSearch(); break;
                case "2": Demo.runScenario1MetadataWorkaround(); break;
                case "3": Demo.runScenario2FatFinger(); break;
                case "4": Demo.runScenario3SchemaAgility(); break;
                case "5": Demo.runScenario4Aggregation(); break;
                case "6": Demo.runScenario5HybridSearch(); break;
                
                case "7": await Demo2.runQueryComparison(); break; // Added 'await' since Prisma is async!
                
                case "0":
                    console.log("Exiting Chapter 8 Demo...");
                    rl.close();
                    return; 
                default:
                    console.log("Invalid choice. Please enter a number between 0 and 7.");
                    continue;
            }
            
            await rl.question("\nPress Enter to return to the main menu...");
        }
    }
}

// Start the application
Chapter8Menu.display();