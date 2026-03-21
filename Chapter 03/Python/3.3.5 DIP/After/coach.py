"""
DIP SOLUTION: Dependency Injection.
* ARCHITECTURE NOTE: The 'Coach' is now completely decoupled from specific 
player types. It no longer 'imports' Forward or Midfielder. 
* By accepting a list of players in the constructor, we have moved the 
responsibility of team assembly (The Composition Root) outside of this class. 
The Coach is now a pure coordinator that works with any object following 
the Player contract.
"""
class Coach:
    def __init__(self, players: list['Player']):
        # Dependency Injection: The team is provided, not created here.
        self.team = players

    def execute_game_plan(self):
        # The Coach relies on the abstraction (.perform_action()) 
        # rather than concrete methods like .attack().
        for player in self.team:
            player.perform_action()