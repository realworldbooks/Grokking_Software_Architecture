from training_session import TrainingSession

"""
ANTI-PATTERN: The Incomplete Implementation.
* ARCHITECTURE PROBLEM: Because Midfielder inherits from TrainingSession, 
it MUST implement all four methods to be instantiated.
* This results in "Code Rot." We are forced to provide dummy implementations 
that throw errors, cluttering our domain model with logic that shouldn't 
exist here in the first place.
"""
class Midfielder(TrainingSession):
    def practice_shooting(self):
        print("  [Midfielder] Practicing shooting drills.")
    
    def practice_tackling(self):
        print("  [Midfielder] Practicing slide tackles.")
    
    # 🚨 ARCHITECTURE WARNING: ISP Violation.
    # The Midfielder is being forced to "know" about goalie drills.
    def practice_diving_saves(self):
        raise NotImplementedError("Midfielders don't play in the net!")
    
    # 🚨 ARCHITECTURE WARNING: ISP Violation.
    def practice_hand_distribution(self):
        raise NotImplementedError("Midfielders can't use their hands!")