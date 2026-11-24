import time

class DatabaseService:
    """
    Simulates a slow, expensive database service.
    ARCHITECTURAL NOTE: In this "Before" state, every single request to the 
    dashboard will be forced to wait for these slow network calls to finish.
    """

    def _simulate_network_latency(self):
        """
        Simulates the real-world latency of network I/O 
        and database query execution time.
        """
        time.sleep(0.5)  # 500ms delay

    def get_profile(self, user_id: str) -> str:
        """Simulates fetching a user profile."""
        print(f"    [DB] Fetching Profile for {user_id}...")
        self._simulate_network_latency()
        print("    [DB] >> Profile data received.")
        return "User_Profile_Data"

    def get_orders(self, user_id: str) -> str:
        """Simulates fetching a user's orders."""
        print(f"    [DB] Fetching Orders for {user_id}...")
        self._simulate_network_latency()
        print("    [DB] >> Order data received.")
        return "User_Orders_Data"

    def get_activity(self, user_id: str) -> str:
        """Simulates fetching a user's activity."""
        print(f"    [DB] Fetching Activity for {user_id}...")
        self._simulate_network_latency()
        print("    [DB] >> Activity data received.")
        return "User_Activity_Data"