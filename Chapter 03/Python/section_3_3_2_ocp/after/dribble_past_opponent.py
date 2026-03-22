from play import Play

"""
A Concrete Strategy.
* ARCHITECTURE NOTE: This class encapsulates one specific behavior. 
Because it's a standalone file, it's easy to test and modify without 
impacting any other part of the system.
"""
class DribblePastOpponent(Play):
    def execute(self):
        print("  [Action] Executing a dribble move…")