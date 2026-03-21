"""
ANTI-PATTERN: Violation of the Open/Closed Principle (OCP).

ARCHITECTURE PROBLEM: The Open/Closed Principle states that a class should be 
"Open for extension, but Closed for modification." 

In this Python implementation, the Midfielder class is NOT closed for 
modification. Every time the team learns a new play (like 'PassToStriker'), 
we are forced to open this file and add a new 'elif' block. 

WHY THIS IS DANGEROUS:
- Regression Risk: Every time you touch this file to add a feature, you risk 
  breaking the existing plays (Dribble or Defensive).
- Maintenance Burden: As the playbook grows, this single method will become 
  a massive, unreadable "if/elif" monster.
- Rigid Design: The Midfielder is tightly coupled to specific string names, 
  making it difficult to swap or share plays across different player types.
"""

class Midfielder:
    def execute_play(self, play_name: str):
        """
        Executes a play based on a hardcoded string. 
        This creates a fragile, infinitely growing conditional chain.
        """
        # 🚨 ARCHITECTURE WARNING: This if/elif chain will grow forever.
        # It forces us to modify tested code to add new functionality.
        if play_name == "DribblePastOpponent":
            print("  [Action] Executing a dribble move…")
        elif play_name == "DefensiveFormation":
            print("  [Action] Getting into defensive position…")
        
        # 🚨 To add 'PassToStriker', we would have to modify this code right here!
        else:
            print(f"  [Error] Unknown play: {play_name}")