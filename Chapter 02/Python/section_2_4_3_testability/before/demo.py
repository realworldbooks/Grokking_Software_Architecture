from .report_generator import ReportGenerator

class Demo:
    """
    Demonstrates the difficulty of testing tightly coupled code.
    """

    @staticmethod
    def run():
        print("--- Testability Example: Dependency Injection (BEFORE) ---")
        print("\n[SCENARIO 1: Before Refactor - Tightly Coupled]")
        print("Attempting to unit test the 'ReportGenerator' class...")
        
        # We instantiate the class. Notice its __init__ immediately creates
        # a RealDatabaseConnection. We have no way to stop this.
        generator = ReportGenerator()
        result = generator.generate("Sales Report")

        # The RealDatabaseConnection returns 2 rows.
        # Our test expects 3 rows.
        expected = "Report 'Sales Report' generated with 3 rows."
        print("  > Verifying the generated report...")
        
        if result != expected:
            print("  ❌ TEST FAILED!")
            print(f"     Expected: \"{expected}\"")
            print(f"     Received: \"{result}\"")
            print("     (This fails because the hardcoded RealDatabaseConnection returns 2 rows, but our test expected 3.)")
        
        print("--------------------------------------------------\n")