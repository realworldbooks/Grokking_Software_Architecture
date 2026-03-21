import time

class DatabaseService:
    """
    Simulates a slow, expensive database service.
    
    NOTE: In a real Python application, I/O-bound operations like this should be
    asynchronous (e.g., using `asyncio` and `httpx` or `asyncpg`) to avoid 
    blocking the execution thread. For simplicity, we use `time.sleep()` here.
    """
    def _simulate_network_latency(self):
        # This simulates the real-world latency of a network request
        # and database query execution.
        time.sleep(0.5)

    def get_profile(self, user_id):
        """Simulates fetching a user profile from the database."""
        print(f"    [DB] Fetching Profile for {user_id}...")
        self._simulate_network_latency()
        print("    [DB] >> Profile data received.")
        return "User_Profile_Data"

    def get_orders(self, user_id):
        """Simulates fetching a user's orders from the database."""
        print(f"    [DB] Fetching Orders for {user_id}...")
        self._simulate_network_latency()
        print("    [DB] >> Order data received.")
        return "User_Orders_Data"

    def get_activity(self, user_id):
        """Simulates fetching a user's activity from the database."""
        print(f"    [DB] Fetching Activity for {user_id}...")
        self._simulate_network_latency()
        print("    [DB] >> Activity data received.")
        return "User_Activity_Data"