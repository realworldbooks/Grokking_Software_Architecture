from .player import Player
"""
Responsibility 3: Data Persistence (The Repository Pattern).
* ARCHITECTURE NOTE: This class handles all infrastructure concerns. It acts 
as a mediator between our domain (Player) and the data mapping layer. 
* Because this logic is isolated, you can change your storage backend from 
an SQL database to a NoSQL store, or even a simple CSV file for local 
testing, by only modifying this repository. The core game logic remains 
completely untouched.
"""
class PlayerRepository:
    def save_stats(self, player: 'Player'):
        """
        Persists the player's statistics to the underlying data store.
        """
        print(f"  [Database] Saving {player.name}'s game stats to the database.")