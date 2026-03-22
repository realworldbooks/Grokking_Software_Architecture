"""
Responsibility 2: Tactical / AI Logic.
* ARCHITECTURE NOTE: This class is a "Service." By moving tactical decisions 
here, we've decoupled the 'Intelligence' of the game from the 'Actor' (the Player). 
* In Python, this allows you to easily swap out this engine for a different 
version—perhaps one driven by Machine Learning or a simple rule-based system—
without ever needing to modify the Player class itself.
"""
class TacticsEngine:
    def determine_best_position(self, player: 'Player'):
        """
        Analyzes the game state to determine the optimal position for a specific player.
        """
        print(f"  [Tactics] Calculating optimal court position for {player.name}...")