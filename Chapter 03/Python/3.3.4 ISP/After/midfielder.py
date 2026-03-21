from interfaces import FieldPlayerTraining

"""
THE CLEAN IMPLEMENTATION.
* ARCHITECTURE NOTE: The Midfielder now only inherits from FieldPlayerTraining. 
It is no longer "haunted" by goalie methods it can't perform. There is 
no longer a need for NotImplementedErrors, making the class "honest" 
and predictable for any consumer.
"""
class Midfielder(FieldPlayerTraining):
    def practice_shooting(self):
        print("  [Midfielder] Practicing shooting drills.")

    def practice_tackling(self):
        print("  [Midfielder] Practicing slide tackles.")