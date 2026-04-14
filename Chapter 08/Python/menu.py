# menu.py
from section_8_1_4_database_comparison.demo import Demo
from section_8_2_1_declarative_querying.demo2 import Demo2

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
            print("\n--- Section 8.1.4: Database Comparison ---")
            print("1. The Literal Search (The Naive Baseline)")
            print("2. The Metadata Workaround (Columns & Tags)")
            print("3. The 'Fat Finger' Test (Fuzzy Intent)")
            print("4. The Schema Agility Test (Business Pivot)")
            print("5. The Aggregation Test (Give Me The Math)")
            print("6. The Hybrid Search (The Holy Grail)")

            print("\n--- Section 8.2.1: Declarative Querying (ORMs) ---")
            
            print("7. Run Query Comparison (Raw SQL vs. SQLAlchemy)\n")
            print("0. Exit")
            print("="*60)
            
            choice = input("\nEnter your choice (0-7): ").strip()

            if choice == '1':
                Demo.run_scenario_0_literal_search()
            elif choice == '2':
                Demo.run_scenario_1_metadata_workaround()
            elif choice == '3':
                Demo.run_scenario_2_fat_finger()
            elif choice == '4':
                Demo.run_scenario_3_schema_agility()
            elif choice == '5':
                Demo.run_scenario_4_aggregation()
            elif choice == '6':
                Demo.run_scenario_5_hybrid_search()
            elif choice == '7':
                Demo2.run_query_comparison()
            
            elif choice == '0':
                print("Exiting Chapter 8 Demo...")
                break
            else:
                print("Invalid choice. Please enter a number between 0 and 7.")
                continue
            
            input("\nPress Enter to return to the main menu...")

if __name__ == "__main__":
    Chapter8Menu.display()