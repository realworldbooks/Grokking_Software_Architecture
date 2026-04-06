const readline = require('readline');
const path = require('path');
const fs = require('fs');

// Load architectural data separated from logic
const config = require('./examples.json');
const examples = config.examples;

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
        console.log(`=== ${config.title} ===\n`);

        for (const [key, example] of Object.entries(examples)) {
            console.log(`${key}. ${example.name}`);
        }
        console.log("\nType 'exit' to quit.");
        
        const choice = (await askQuestion("\nEnter your choice: ")).trim().toLowerCase();
        
        if (choice === 'exit') {
            console.log("Exiting menu...");
            rl.close();
            break;
        } 
        
        if (examples[choice]) {
            const relativePath = examples[choice].path;
            const name = examples[choice].name;
            const isServer = examples[choice].isServer;
            
            const scriptPath = path.join(__dirname, relativePath);
            
            clearScreen();
            
            if (!fs.existsSync(scriptPath)) {
                console.log(`[ERROR] Could not find file: ${scriptPath}`);
                await askQuestion("\nPress Enter to return to the menu...");
                continue; 
            }

            console.log(`--- Running ${name} ---\n`);
            
            try {
                // Clear cache to allow multiple runs in one session
                delete require.cache[require.resolve(scriptPath)];
                const Demo = require(scriptPath);
                
                if (Demo && typeof Demo.run === 'function') {
                    // Call the static run method
                    const activeInstance = await Demo.run();
                    
                    if (isServer) {
                        await askQuestion("\nPress Enter to stop the server and return to the main menu...");
                        if (activeInstance && typeof activeInstance.close === 'function') {
                            activeInstance.close();
                            console.log("Server shut down successfully.");
                        }
                    } else {
                        await askQuestion("\nPress Enter to return to the main menu...");
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

main();