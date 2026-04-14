// menu.js
import * as readline from 'readline/promises';
import { stdin as input, stdout as output } from 'process';
import { Demo } from './section_8_1_4_database_comparison/demo.js';

/**
 * THE UI CONTROLLER (Separation of Concerns):
 * By moving the interactive menu into its own file, we keep our architecture clean.
 * This file handles the user experience, while demo.js handles the database logic.
 * * Note: In Node.js, 'index.js' or 'app.js' is the conventional entry point, however
 *         we are using 'menu.js' since this is a menu driven approach.
 */
class Chapter8Menu {
    static async display() {
        const rl = readline.createInterface({ input, output });

        while (true) {
            console.log("\n============================================================");
            console.log("=== Chapter 8: SQL vs. NoSQL vs. Vector ===");
            console.log("============================================================");
            console.log("0. The Literal Search (The Naive Baseline)");
            console.log("1. The Metadata Workaround (Columns & Tags)");
            console.log("2. The 'Fat Finger' Test (Fuzzy Intent)");
            console.log("3. The Schema Agility Test (Business Pivot)");
            console.log("4. The Aggregation Test (Give Me The Math)");
            console.log("5. The Hybrid Search (The Holy Grail)");
            console.log("6. Exit");
            console.log("============================================================");
            
            const choice = (await rl.question("\nEnter your choice (0-6): ")).trim();

            switch (choice) {
                case "0":
                    Demo.runScenario0LiteralSearch();
                    break;
                case "1":
                    Demo.runScenario1MetadataWorkaround();
                    break;
                case "2":
                    Demo.runScenario2FatFinger();
                    break;
                case "3":
                    Demo.runScenario3SchemaAgility();
                    break;
                case "4":
                    Demo.runScenario4Aggregation();
                    break;
                case "5":
                    Demo.runScenario5HybridSearch();
                    break;
                case "6":
                    console.log("Exiting Chapter 8 Demo...");
                    rl.close();
                    return; // Exits the application
                default:
                    console.log("Invalid choice. Please enter a number between 0 and 6.");
                    continue;
            }
            
            await rl.question("\nPress Enter to return to the main menu...");
        }
    }
}

// Start the application
Chapter8Menu.display();