const readline = require('readline');
const path = require('path');
const fs = require('fs');

// ARCHITECTURAL NOTE: Data is completely separated from logic!
const examples = require('./examples.json');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

function clearScreen() {
    console.clear();
}

function askQuestion(query) {
    return new Promise(resolve => rl.question(query, resolve));
}

async function main() {
    while (true) {
        clearScreen();
        console.log("=== Grokking Software Architecture Chapter 04: JS Examples ===\n");

        for (const [key, example] of Object.entries(examples)) {
            // Supports both 'name' and 'title' depending on how you formatted your JSON
            console.log(`${key}. ${example.name || example.title}`);
        }
        console.log("\nType 'exit' to quit.");
        
        const choice = (await askQuestion("\nEnter your choice: ")).trim().toLowerCase();
        
        if (choice === 'exit') {
            console.log("Exiting menu...");
            rl.close();
            break;
        } 
        
        if (examples[choice]) {
            // Extract the relative path from the JSON config
            const relativePath = examples[choice].path;
            const name = examples[choice].name || examples[choice].title;
            
            // Build the absolute path dynamically at runtime
            const scriptPath = path.join(__dirname, relativePath);
            
            clearScreen();
            
            // BULLETPROOF CHECK: Does the file actually exist?
            if (!fs.existsSync(scriptPath)) {
                console.log(`[ERROR] Could not find the file for ${name}.`);
                console.log(`Looked in: ${scriptPath}`);
                console.log("\nPlease check your folder names and update the path in examples.json.");
                await askQuestion("\nPress Enter to return to the menu...");
                continue; 
            }

            console.log(`--- Running ${name} ---\n`);
            
            try {
                // 1. Clear the cache so you can run the same demo multiple times 
                delete require.cache[require.resolve(scriptPath)];
                
                // 2. Load the module dynamically
                const Demo = require(scriptPath);
                
                // 3. Call the static run method and capture the server instance
                if (Demo && typeof Demo.run === 'function') {
                    // Await the run method in case it returns a server Promise
                    const activeServer = await Demo.run();
                    
                    // Pause the menu while you test the API
                    await askQuestion("\nPress Enter to stop the server and return to the main menu...");

                    // 4. Gracefully shut down the Express server if one was returned
                    if (activeServer && typeof activeServer.close === 'function') {
                        activeServer.close();
                        console.log("Web server shut down successfully.");
                    }
                } else {
                    console.log("[ERROR] Demo class or exported run() method not found.");
                    await askQuestion("\nPress Enter to return to the main menu...");
                }
            } catch (err) {
                console.log(`[ERROR] Execution failed: ${err.message}`);
                await askQuestion("\nPress Enter to return to the main menu...");
            }
        } else {
            console.log("Invalid choice. Please try again.");
            await askQuestion("\nPress Enter to continue...");
        }
    }
}

// Start the application
main();