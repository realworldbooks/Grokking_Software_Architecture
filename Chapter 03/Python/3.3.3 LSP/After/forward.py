from player import Player

"""
LSP SOLUTION: True Substitutability.
* ARCHITECTURE NOTE: The Forward is a true substitute for a Player. It 
doesn't provide "surprising" behavior or refuse the contract. When the 
Coach expects a field action, the Forward delivers exactly that.
"""
class Forward(Player):
    def play_field_position(self):
        print("  [Forward] Leading the attack and trying to score.")