"""
Represents a user entity.
ARCHITECTURAL NOTE: Structural Constraints
By isolating this model, we ensure the shape of our data is decoupled 
from retrieval or processing logic.
"""

class User:
    def __init__(self, user_id: str, name: str, email: str):
        # Python doesn't have a 'required' keyword like C#, so we 
        # enforce the constraint by validating during initialization.
        if not all([user_id, name, email]):
            raise ValueError("User must have an id, name, and email.")
            
        self.id = user_id
        self.name = name
        self.email = email