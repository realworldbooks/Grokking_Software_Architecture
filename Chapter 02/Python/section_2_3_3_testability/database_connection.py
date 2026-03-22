class DatabaseConnection:
    """
    This is the "real" or "production" implementation of a database connection.
    In a real-world application, this class would use a library like `psycopg2`
    or an ORM like SQLAlchemy to interact with a live database.
    """
    def __init__(self, connection_string):
        """
        Initializes the real database connection.
        
        Args:
            connection_string (str): The database connection string.
        """
        # In a real application, this is where the connection would be established.
        print(f"\n  [DB] Connecting to... {connection_string}")
        
    def get_data(self, query):
        """
        Fetches data from the live database.
        
        Args:
            query (str): The query to execute.
            
        Returns:
            list: A list of data rows from the real database.
        """
        # For demonstration purposes, we're just returning hardcoded data.
        print(f"  [DB] Executing query: {query}")
        return ["real_data_row1", "real_data_row2"]