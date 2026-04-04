import json
import os
import importlib
import asyncio
import inspect

def clear_screen():
    """Clears the terminal for a professional UI experience."""
    os.system('cls' if os.name == 'nt' else 'clear')

async def main():
    """Main execution loop for the Chapter 05 Python examples."""
    # Using lowercase as established in the JS chapter
    config_path = 'examples.json'

    if not os.path.exists(config_path):
        print(f"[ERROR] {config_path} not found in the current directory!")
        return

    with open(config_path, 'r') as f:
        examples = json.load(f)

    while True:
        clear_screen()
        print("=== Grokking Software Architecture Chapter 05: Python Examples ===\n")

        # Sort keys numerically to ensure a logical progression for the user
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
            print(f"--- Running: {selected['name']} ---\n")

            try:
                # Dynamic Loading: Python's version of Reflection
                module = importlib.import_module(selected['path'])
                
                # Force a reload in case the user edited the file between runs
                importlib.reload(module)
                
                demo_class = getattr(module, 'Demo', None)

                if demo_class and hasattr(demo_class, 'run'):
                    # Check if the run method is a coroutine to handle async demos
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
            input("\nInvalid choice. Press Enter to try again...")

if __name__ == "__main__":
    # Supports our asynchronous 'After' scenarios
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        pass