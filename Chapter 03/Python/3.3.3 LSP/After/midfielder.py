from player import Player

"""
LSP SOLUTION: True Substitutability.
* ARCHITECTURE NOTE: Just like the Forward, the Midfielder is 100% 
compatible with the Player abstraction. We can swap one for the other 
at runtime without the Coach ever needing to know the difference.
"""
class Midfielder(Player):
    def play_field_position(self):
        print("  [Midfielder] Controlling the midfield, passing and tackling.")