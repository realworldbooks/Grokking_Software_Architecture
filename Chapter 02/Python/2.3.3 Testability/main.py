from report_generator_before import ReportGeneratorBefore
from report_generator_after import ReportGeneratorAfter

class FakeDatabase:
    def get_data(self, query):
        return ["row1", "row2", "row3"]

if __name__ == "__main__":
    print("=== Chapter 2: Testability Example ===")

    print("\n--- Running Before: Tightly Coupled Test ---")
    generator_before = ReportGeneratorBefore()
    result_before = generator_before.generate("FailingTest")
    expected_before = "Report 'FailingTest' generated with 3 rows."
    
    if result_before != expected_before:
        print("❌ TEST FAILED!")
        print(f"  Expected: \"{expected_before}\"")
        print(f"  Received: \"{result_before}\"")

    print("\n--- Running After: Loosely Coupled Test ---")
    generator_after = ReportGeneratorAfter(FakeDatabase())
    result_after = generator_after.generate("PassingTest")
    expected_after = "Report 'PassingTest' generated with 3 rows."
    
    if result_after == expected_after:
        print(f"✅ TEST PASSED! Received expected result: \"{result_after}\"")
        
    print("\n======================================")