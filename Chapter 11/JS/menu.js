import readline from 'readline';
import path from 'path';
import fs from 'fs';
import { fileURLToPath } from 'url';

// Setup __dirname for ES Modules
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Load architectural data separated from logic (flat schema: the whole file is the examples map)
const configPath = path.join(__dirname, 'examples.json');
const examples = JSON.parse(fs.readFileSync(configPath, 'utf8'));

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
        console.log("=== Grokking Software Architecture Chapter 11: Secrets Management ===\n");

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
                // In ES modules, dynamic imports are cached. We use a timestamp 
                // query string to bust the cache so the file runs fresh every time.
                const cacheBusterPath = `file://${scriptPath}?update=${Date.now()}`;
                
                const module = await import(cacheBusterPath);
                const Demo = module.Demo;
                
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