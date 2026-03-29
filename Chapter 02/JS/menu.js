const readline = require('readline');
const { spawnSync } = require('child_process');
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
console.log("=== Grokking Software Architecture Chapter 02: JS Examples ===\n");

    for (const [key, example] of Object.entries(examples)) {
        console.log(`${key}. ${example.name}`);
    }
    console.log("\nType 'exit' to quit.");
    
    const choice = (await askQuestion("\nEnter your choice: ")).trim().toLowerCase();
    
    if (choice === 'exit') {
        rl.close();
        break;
    } else if (examples[choice]) {
        // Extract the relative path from the JSON config
        const { name, path: relativePath } = examples[choice];
        
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
            
            // 3. Call the static run method
            if (Demo && typeof Demo.run === 'function') {
                Demo.run();
            } else {
                console.log("[ERROR] Demo class or static run() method not found.");
            }
        } catch (err) {
            console.log(`[ERROR] Execution failed: ${err.message}`);
        }
        
        await askQuestion("\nPress Enter to return to the main menu...");
    } else {
        console.log("Invalid choice. Please try again.");
        await askQuestion("\nPress Enter to continue...");
    }
}
}

main();