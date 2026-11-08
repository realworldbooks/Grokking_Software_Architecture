const fs = require('fs');
const path = require('path');
const readline = require('readline');

/**
 * THE MENU DRIVER.
 * Acts as the 'Chief Explainer' for the JavaScript chapter.
 * Dynamically loads architectural examples based on the examples.json configuration.
 */
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

/**
 * Displays the interactive menu and handles user selection.
 * Uses recursion to return to the menu after an example finishes.
 */
async function showMenu() {
    // We use path.join to stay consistent with the OS's file resolution
    const configPath = path.join(__dirname, 'examples.json');

    if (!fs.existsSync(configPath)) {
        console.error(`[ERROR] examples.json not found at: ${configPath}`);
        process.exit(1);
    }

    const examples = JSON.parse(fs.readFileSync(configPath, 'utf8'));

    clearScreen();
    console.log("\n=== Grokking Software Architecture Chapter 05: JS Examples ===\n");

    // Standardized sorting to match the Java and C# menus
    Object.keys(examples).sort((a, b) => parseInt(a) - parseInt(b)).forEach(key => {
        console.log(`${key}. ${examples[key].name}`);
    });

    console.log("\nType 'exit' to quit.");
    const choice = (await askQuestion("\nEnter your choice: ")).trim().toLowerCase();

    if (choice === 'exit') {
        rl.close();
        return;
    }

    const selected = examples[choice];

    if (selected) {
        // Path.resolve ensures the 'JS' folder is handled correctly regardless of case
        const scriptPath = path.resolve(__dirname, selected.path);

        if (!fs.existsSync(scriptPath)) {
            console.log(`\n[ERROR] Could not find file: ${scriptPath}`);
            return showMenu();
        }

        clearScreen();
        console.log(`\n--- Running: ${selected.name} ---\n`);

        try {
            // Wipe the Node.js module cache so the demo can be re-run in the same session
            delete require.cache[require.resolve(scriptPath)];
            const Demo = require(scriptPath);

            // Verify the entry point exists before calling it
            if (Demo && typeof Demo.run === 'function') {
                // Await the run method so async output completes before we prompt
                await Demo.run();
            } else {
                console.log("[ERROR] Demo class or static run() method not found.");
            }
        } catch (err) {
            console.error(`[ERROR] Execution failed: ${err.message}`);
            console.error(err.stack);
        }

        // Provide spacing so results don't crash into the menu prompt
        console.log("\n----------------------------------------");
        await askQuestion("\n[DONE] Press Enter to return to the menu...");
        return showMenu();
    }

    console.log("\nInvalid choice. Please try again.");
    return showMenu();
}

// Kick off the interactive session
showMenu();