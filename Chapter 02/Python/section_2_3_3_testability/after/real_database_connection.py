from .database_connection import IDatabaseConnection

class RealDatabaseConnection(IDatabaseConnection):
    """
    The 'real' production implementation of the IDatabaseConnection.
    """

    def __init__(self, connection_string: str):
        self._connection_string = connection_string
        print(f"\n  [DB] Connecting to... {self._connection_string}")

    def get_data(self, query: str) -> list[str]:
        print(f"  [DB] Executing query: {query}")
        return ["real_data_row1", "real_data_row2"]