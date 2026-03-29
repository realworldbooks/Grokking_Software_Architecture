import json
import os
import importlib
import asyncio
import inspect

def clear_screen():
    os.system('cls' if os.name == 'nt' else 'clear')

async def main():
    config_path = 'examples.json'

    # BULLETPROOF CHECK: Does the config file exist?
    if not os.path.exists(config_path):
        print(f"[ERROR] {config_path} not found!")
        return

    with open(config_path, 'r') as f:
        examples = json.load(f)

    while True:
        clear_screen()
        print("=== Grokking Software Architecture Chapter 04: Python Examples ===\n")

        # Sort keys numerically for a clean menu
        keys = sorted(examples.keys(), key=lambda x: int(x))

        for key in keys:
            print(f"{key}. {examples[key]['name']}")

        print("\nType 'exit' to quit.")
        choice = input("\nEnter your choice: ").strip()

        if choice.lower() == 'exit':
            break

        if choice in examples:
            selected = examples[choice]
            clear_screen()
            print(f"--- Running {selected['name']} ---\n")

            try:
                # ARCHITECTURAL NOTE: Dynamic Loading (Python's version of Reflection)
                # We import the module and look for a class named 'Demo'
                module = importlib.import_module(selected['path'])
                demo_class = getattr(module, 'Demo', None)

                if demo_class and hasattr(demo_class, 'run'):
                    # Check if the run method is a coroutine (async)
                    if inspect.iscoroutinefunction(demo_class.run):
                        await demo_class.run()
                    else:
                        demo_class.run()
                else:
                    print(f"[ERROR] Could not find class 'Demo' or method 'run' in {selected['path']}")

            except Exception as e:
                print(f"[ERROR] Execution failed: {str(e)}")
            
            input("\nPress Enter to return to the main menu...")
        else:
            input("Invalid choice. Press Enter to try again...")

if __name__ == "__main__":
    # Using asyncio.run to support our asynchronous 'Constraints' example
    asyncio.run(main())