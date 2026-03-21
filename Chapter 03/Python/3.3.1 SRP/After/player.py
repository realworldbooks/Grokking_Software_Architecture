"""
Responsibility 1: Player State and Core Actions.
* ARCHITECTURE NOTE: By stripping away infrastructure and AI logic, the Player 
class has become a "Pure Domain Model." It is now highly cohesive, meaning 
every line of code inside this class relates directly to what a player IS. 
* This is now an "Anemic-friendly" or "Rich" model that can be easily 
serialized, moved across network boundaries, or used in different game 
modes without dragging along a database connection.
"""
class Player:
    def __init__(self, name: str):
        self.name = name

    def dribble_ball(self):
        """
        Executes a core domain action.
        """
        print(f"  [Action] {self.name} is dribbling the ball down the court.")