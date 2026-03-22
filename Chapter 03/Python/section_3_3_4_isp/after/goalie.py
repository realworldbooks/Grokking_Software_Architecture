from interfaces import FieldPlayerTraining, GoalieTraining

"""
INTERFACE COMPOSITION.
* ARCHITECTURE NOTE: Python handles ISP beautifully through multiple 
inheritance. Because a Goalie needs both general field skills and 
specialized net skills, we simply compose the Goalie out of both 
interfaces. 
* This provides total flexibility without polluting the base definitions 
for other players.
"""
class Goalie(FieldPlayerTraining, GoalieTraining):
    def practice_shooting(self):
        print("  [Goalie] Practicing goal kicks and long shots.")

    def practice_tackling(self):
        print("  [Goalie] Practicing 1-on-1 box tackles.")

    def practice_diving_saves(self):
        print("  [Goalie] Practicing top-corner diving saves.")

    def practice_hand_distribution(self):
        print("  [Goalie] Practicing fast break throws.")