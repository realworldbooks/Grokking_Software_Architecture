from player import Player

"""
A Low-Level Detail.
* ARCHITECTURE NOTE: By inheriting from the Player ABC, these classes 
explicitly sign the contract. They are now "plug-and-play" components 
that the Coach can lead without needing to know their specific types.
"""
class Midfielder(Player):
    def perform_action(self):
        print("  [Action] Midfielder is controlling the game.")