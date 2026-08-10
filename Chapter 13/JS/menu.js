import fs from "fs";
import path from "path";
import { fileURLToPath } from "url";
import readline from "readline";

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const configPath = path.join(__dirname, "examples.json");

const rl = readline.createInterface({ input: process.stdin, output: process.stdout });
const question = (q) => new Promise((res) => rl.question(q, res));

const clearScreen = () => { if (process.stdout.isTTY) process.stdout.write("\x1b[2J\x1b[0f"); };

const executeDemo = async (modulePath) => {
  try {
    const mod = await import(path.join(__dirname, modulePath));
    const DemoClass = mod.Demo;
    if (!DemoClass) { console.error("[ERROR] No Demo class found in " + modulePath); return; }
    await DemoClass.run();
  } catch (e) { console.error("[ERROR] Execution failed: " + e.message); }
};

async function main() {
  if (!fs.existsSync(configPath)) { console.error("[ERROR] " + configPath + " not found!"); return; }
  const examples = JSON.parse(fs.readFileSync(configPath, "utf-8"));

  // eslint-disable-next-line no-constant-condition
  while (true) {
    clearScreen();
    console.log("=== Grokking Software Architecture Chapter 13: Node.js Examples ===\n");
    for (const key of Object.keys(examples)) console.log(key + ". " + examples[key].name);
    console.log("\nType 'exit' to quit.");
    const choice = (await question("\nEnter your choice: ")).trim().toLowerCase();

    if (choice === "exit") break;
    if (examples[choice]) {
      clearScreen();
      console.log("--- Running " + examples[choice].name + " ---\n");
      await executeDemo(examples[choice].path);
      await question("\nPress Enter to return to the main menu...");
    } else {
      await question("Invalid choice. Press Enter to try again...");
    }
  }
  rl.close();
}

main();