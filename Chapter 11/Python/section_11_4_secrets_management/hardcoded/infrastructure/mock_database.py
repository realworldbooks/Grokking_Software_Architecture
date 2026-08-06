"""
MOCK DATABASE CONNECTION - FOR DEMONSTRATION ONLY

This simulates a database connection to show the hardcoded credentials problem.
In reality, this would be psycopg2.connect() or similar.
"""

class MockDatabaseConnection:
    """
    Simulates a database connection that requires credentials.
    """
    
    def __init__(self, host: str, database: str, user: str, password: str):
        self.host = host
        self.database = database
        self.user = user
        self.password = password
        self._is_connected = False
    
    def connect(self) -> bool:
        """
        Simulates establishing a database connection.
        In a real scenario, this would call psycopg2.connect() or similar.
        """
        # Simulate connection logic
        if self.user == "admin" and self.password == "Password123!":
            self._is_connected = True
            return True
        else:
            raise ConnectionError(f"Authentication failed for user {self.user}")
    
    def execute_query(self, query: str) -> list:
        """
        Simulates executing a database query.
        """
        if not self._is_connected:
            raise ConnectionError("Not connected to database")
        
        # Return mock data
        return [{"order_id": "ORD-001", "customer_id": "CUST-123", "total": 99.99}]
    
    def close(self):
        """Close the database connection."""
        self._is_connected = False