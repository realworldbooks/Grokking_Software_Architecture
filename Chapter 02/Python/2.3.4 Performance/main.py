import time
from dashboard_before import DashboardBefore
from dashboard_after import DashboardAfter

def run_performance_demo():
    """
    Runs the demonstration for the Performance chapter.
    """
    print("--- Performance Example: Caching ---")
    USER_ID = "user123"

    # --- SCENARIO 1: The "Before" Case (No Caching) ---
    print("\\n[SCENARIO 1: Before Refactor - No Caching]")
    dashboard_before = DashboardBefore()
    
    start_time = time.time()
    dashboard_before.get_dashboard_summary(USER_ID)
    end_time = time.time()
    print(f"\\n>> Time taken: { (end_time - start_time) * 1000:.0f}ms")

    # --- SCENARIO 2: The "After" Case (With Caching) ---
    print("\\n[SCENARIO 2: After Refactor - With Cache-Aside Pattern]")
    dashboard_after = DashboardAfter()

    # First call for a user is a "cache miss". This call will be slow.
    print("\\n(First call for a new user... expect a cache miss)")
    start_time = time.time()
    dashboard_after.get_dashboard_summary(USER_ID)
    end_time = time.time()
    print(f"\\n>> Time taken: { (end_time - start_time) * 1000:.0f}ms")

    # The second call is a "cache hit" and will be dramatically faster.
    print("\\n(Second call for the same user... expect a cache hit)")
    start_time = time.time()
    dashboard_after.get_dashboard_summary(USER_ID)
    end_time = time.time()
    print(f"\\n>> Time taken: { (end_time - start_time) * 1000:.0f}ms")
    print("--------------------------------------\\n")

if __name__ == "__main__":
    run_performance_demo()