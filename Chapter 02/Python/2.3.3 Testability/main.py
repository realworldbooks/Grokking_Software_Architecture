from report_generator_before import ReportGeneratorBefore
from report_generator_after import ReportGeneratorAfter

# This is our "Fake" or "Mock" class. It's a "Test Double" for the real database.
# Its purpose is to be used in tests. It doesn't connect to any real database;
# it just has a `get_data` method that returns predictable, hardcoded data,
# satisfying the "duck typing" contract that `ReportGeneratorAfter` expects.
class FakeDatabase:
    def get_data(self, query):
        print(f"\\n  [FAKE DB] Received query: {query}. Returning fake data.")
        return ["fake_row1", "fake_row2", "fake_row3"]

def run_testability_demo():
    """
    Runs the demonstration for the Testability chapter.
    """
    print("--- Testability Example: Dependency Injection ---")

    # --- SCENARIO 1: The "Before" Case (Tightly Coupled) ---
    print("\\n[SCENARIO 1: Before Refactor - Tightly Coupled]")
    print("Attempting to unit test the 'ReportGeneratorBefore' class...")
    
    # We instantiate the class. Its constructor immediately creates a `DatabaseConnection`.
    generator_before = ReportGeneratorBefore()
    result_before = generator_before.generate("Sales Report")
    
    # The real `DatabaseConnection` returns 2 rows, but our test expects 3.
    # This test will fail. More importantly, this is an integration test, not a unit test.
    expected_before = "Report 'Sales Report' generated with 3 rows."
    print("  > Verifying the generated report...")
    if result_before != expected_before:
        print("  ❌ TEST FAILED!")
        print(f"     Expected: \\"{expected_before}\\"")
        print(f"     Received: \\"{result_before}\\"")
        print("     (This fails because the hardcoded DatabaseConnection returns 2 rows, but our test expected 3.)")

    # --- SCENARIO 2: The "After" Case (Loosely Coupled) ---
    print("\\n[SCENARIO 2: After Refactor - Loosely Coupled with Dependency Injection]")
    print("Unit testing the 'ReportGeneratorAfter' class with a mock object...")
    
    # We "inject" an instance of our `FakeDatabase` into the constructor.
    # The generator is happy because it receives an object with a `get_data` method.
    generator_after = ReportGeneratorAfter(FakeDatabase())
    result_after = generator_after.generate("Sales Report")
    
    # Our fake database returns 3 rows, so our test assertion passes.
    # This is a true, fast, and reliable unit test.
    expected_after = "Report 'Sales Report' generated with 3 rows."
    print("  > Verifying the generated report...")
    if result_after == expected_after:
        print(f"  ✅ TEST PASSED! Received expected result: \\"{result_after}\\"")
    else:
        print("  ❌ TEST FAILED!")
        print(f"     Expected: \\"{expected_after}\\"")
        print(f"     Received: \\"{result_after}\\"")

    print("--------------------------------------------------\\n")

if __name__ == "__main__":
    run_testability_demo()