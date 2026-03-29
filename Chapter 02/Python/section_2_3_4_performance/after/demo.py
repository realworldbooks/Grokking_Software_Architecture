import time
from .dashboard import Dashboard

class Demo:
    """
    Demonstrates the dramatic performance gains of the Cache-Aside pattern.
    """

    @staticmethod
    def run():
        print("--- Performance Example: Caching (AFTER) ---")
        print("\n[SCENARIO 2: After Refactor - With Cache-Aside Pattern]")
        
        user_id = "user123"
        dashboard = Dashboard()

        # FIRST CALL: Cache Miss (Expect ~1500ms)
        print("\n(First call for a new user... expect a cache miss)")
        start_1 = time.perf_counter()
        dashboard.get_dashboard_summary(user_id)
        end_1 = time.perf_counter()
        print(f"\n>> Time taken: {(end_1 - start_1) * 1000:.2f}ms")

        # SECOND CALL: Cache Hit (Expect ~0ms)
        print("\n(Second call for the same user... expect a cache hit)")
        start_2 = time.perf_counter()
        dashboard.get_dashboard_summary(user_id)
        end_2 = time.perf_counter()
        print(f"\n>> Time taken: {(end_2 - start_2) * 1000:.2f}ms")
        
        print("--------------------------------------------------\n")