from .report_generator import ReportGenerator
from .fake_database_connection import FakeDatabaseConnection

class Demo:
    """
    Demonstrates the power of Dependency Injection in testing.
    """

    @staticmethod
    def run():
        print("--- Testability Example: Dependency Injection (AFTER) ---")
        print("\n[SCENARIO 2: After Refactor - Loosely Coupled with Dependency Injection]")
        print("Unit testing the 'ReportGenerator' class with a mock database...")

        # We create the fake connection and "inject" it.
        fake_db = FakeDatabaseConnection()
        generator = ReportGenerator(fake_db)
        
        result = generator.generate("Sales Report")
        
        # Our fake database returns 3 rows, so the unit test passes.
        expected = "Report 'Sales Report' generated with 3 rows."
        print("  > Verifying the generated report...")
        
        if result == expected:
            print(f"  ✅ TEST PASSED! Received expected result: \"{result}\"")
        else:
            print(f"  ❌ TEST FAILED! Received: \"{result}\"")
        
        print("--------------------------------------------------\n")