import time
from .dashboard import Dashboard

class Demo:
    """
    Demonstrates the slow performance of a non-cached dashboard.
    """

    @staticmethod
    def run():
        print("--- Performance Example: Caching (BEFORE) ---")
        print("\n[SCENARIO 1: Before Refactor - No Caching]")
        print("Notice how slow this is. Every request hits the database.\n")
        
        user_id = "user123"
        dashboard = Dashboard()
        
        start_time = time.perf_counter()
        dashboard.get_dashboard_summary(user_id)
        end_time = time.perf_counter()
        
        duration_ms = (end_time - start_time) * 1000
        print(f"\n>> Time taken: {duration_ms:.2f}ms")
        print("--------------------------------------------------\n")