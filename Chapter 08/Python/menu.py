# menu.py
from section_8_1_4_database_comparison.demo import Demo

class Chapter8Menu:
    """
    THE UI CONTROLLER (Separation of Concerns):
    By moving the interactive menu into its own file, we keep our architecture clean.
    This file handles the user experience, while demo.py handles the database logic.
    """
    
    @staticmethod
    def display() -> None:
        while True:
            print("\n" + "="*60)
            print("=== Chapter 8: SQL vs. NoSQL vs. Vector ===")
            print("="*60)
            print("0. The Literal Search (The Naive Baseline)")
            print("1. The Metadata Workaround (Columns & Tags)")
            print("2. The 'Fat Finger' Test (Fuzzy Intent)")
            print("3. The Schema Agility Test (Business Pivot)")
            print("4. The Aggregation Test (Give Me The Math)")
            print("5. The Hybrid Search (The Holy Grail)")
            print("6. Exit")
            print("="*60)
            
            choice = input("\nEnter your choice (0-6): ").strip()

            if choice == '0':
                Demo.run_scenario_0_literal_search()
            elif choice == '1':
                Demo.run_scenario_1_metadata_workaround()
            elif choice == '2':
                Demo.run_scenario_2_fat_finger()
            elif choice == '3':
                Demo.run_scenario_3_schema_agility()
            elif choice == '4':
                Demo.run_scenario_4_aggregation()
            elif choice == '5':
                Demo.run_scenario_5_hybrid_search()
            elif choice == '6':
                print("Exiting Chapter 8 Demo...")
                break
            else:
                print("Invalid choice. Please enter a number between 0 and 6.")
                continue
            
            input("\nPress Enter to return to the main menu...")

if __name__ == "__main__":
    Chapter8Menu.display()