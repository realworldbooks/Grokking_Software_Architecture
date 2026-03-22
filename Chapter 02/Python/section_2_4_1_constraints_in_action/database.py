class Database:
    """
    Simulates a Data Access Layer (DAL) or "Service" class.
    Its single responsibility is to handle all interactions with the database.
    This separation of concerns is a key architectural principle.
    """
    def fetch_user_data(self, user_id):
        """
        Fetches a user's data from the database.
        
        Args:
            user_id (str): The ID of the user to fetch.
            
        Returns:
            A dictionary with user data if found, otherwise None.
            Returning None is an explicit design choice to signal that the user
            was not found, allowing the caller to handle this case.
        """
        # In a real app, this would be an async database call.
        if user_id == "User123":
            return {
                "id": "User123",
                "name": "Alice",
                "email": "alice@example.com"
            }
        return None