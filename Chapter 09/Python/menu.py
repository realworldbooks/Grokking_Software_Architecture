from demo import Demo

class Chapter9Menu:
    """
    THE UI CONTROLLER (Separation of Concerns):
    Handles the user experience for Chapter 9.
    """
    
    @staticmethod
    def display() -> None:
        while True:
            print("\n" + "="*60)
            print("=== Chapter 9: Cloud Native & Stateless Architecture ===")
            print("="*60)
            
            print("\n--- Section 9.1: Stateful vs. Stateless Design ---")
            print("1. Run Stateful Scenario (The Fragile Monolith)")
            print("2. Run Stateless Scenario (Cloud Native S3)")
            
            print("\n0. Exit")
            print("="*60)
            
            choice = input("\nEnter your choice (0-2): ").strip()

            if choice == '1':
                Demo.run_stateful_scenario()
            elif choice == '2':
                Demo.run_stateless_scenario()
            elif choice == '0':
                print("Exiting Chapter 9 Demo...")
                break
            else:
                print("Invalid choice. Please enter a number between 0 and 2.")
                continue
            
            input("\nPress Enter to return to the main menu...")

if __name__ == "__main__":
    Chapter9Menu.display()