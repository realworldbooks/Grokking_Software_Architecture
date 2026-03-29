"""
This is the "real" or "production" implementation of the database connection.
It would contain the actual logic to connect to and query a live database.

ARCHITECTURAL NOTE: In this "Before" state, notice that this class does not 
implement any abstract base class or interface. It is a rigid, concrete implementation.
"""

class RealDatabaseConnection:
    def __init__(self, connection_string: str):
        """
        Initializes the real database connection.
        """
        self._connection_string = connection_string
        # In a real application, this is where the connection would be established.
        print(f"\n  [DB] Connecting to... {self._connection_string}")

    def get_data(self, query: str) -> list[str]:
        """
        Fetches data from the live database.
        """
        # For demonstration purposes, we're just returning hardcoded data.
        print(f"  [DB] Executing query: {query}")
        return ["real_data_row1", "real_data_row2"]