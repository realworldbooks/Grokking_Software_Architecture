import json
import os
import importlib
import asyncio
import inspect

def clear_screen():
    os.system('cls' if os.name == 'nt' else 'clear')

def main():
    config_path = 'examples.json'

    # BULLETPROOF CHECK: Does the config file exist?
    if not os.path.exists(config_path):
        print(f"[ERROR] {config_path} not found!")
        return

    with open(config_path, 'r') as f:
        examples = json.load(f)

    while True:
        # clear_screen() # Keep clear_screen for better user experience
        print("=== Grokking Software Architecture Chapter 06: Python Examples ===\n")

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
            # clear_screen() # Keep clear_screen for better user experience
            print(f"--- Running {selected['name']} ---\n")

            try:
                module = importlib.import_module(selected['path'])
                demo_class = getattr(module, 'Demo', None)

                if demo_class and hasattr(demo_class, 'run'):
                    # Check if the example itself is an async function
                    if inspect.iscoroutinefunction(demo_class.run):
                        asyncio.run(demo_class.run())
                    else:
                        # Standard execution (Allows Uvicorn to manage its own event loop)
                        demo_class.run()
                else:
                    print(f"[ERROR] Could not find class 'Demo' or method 'run' in {selected['path']}")

            except Exception as e:
                print(f"[ERROR] Execution failed: {str(e)}")
            
            input("\nPress Enter to return to the main menu...")
        else:
            input("Invalid choice. Press Enter to try again...")

if __name__ == "__main__":
    # Don't use asyncio.run() so Uvicorn can take control
    main()