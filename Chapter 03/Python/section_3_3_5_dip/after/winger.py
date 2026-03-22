from player import Player

class Winger(Player):
    """
    DIP PROOF: We can add this new player type and inject it into 
    the Coach without modifying a single line of coach.py!
    """
    def perform_action(self):
        print("  [Action] Winger is running down the sideline.")