from typing import Optional
from .user import User

class Database:
    """
    Simulates a Data Access Layer (DAL).
    Single responsibility: handle all interactions with the database.
    """

    async def fetch_user_data_async(self, user_id: str) -> Optional[User]:
        """
        Fetches a user's data from the database.
        
        ARCHITECTURAL NOTE: The Nullable Constraint
        Returning Optional[User] (which can be None) forces the caller 
        to handle the case where a user doesn't exist.
        """
        # Simulating an asynchronous database call
        if user_id == "User123":
            return User(
                user_id="User123", 
                name="Alice", 
                email="alice@example.com"
            )
        
        # Signal the user was not found
        return None