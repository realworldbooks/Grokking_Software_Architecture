from play import Play

"""
A Concrete Strategy.
* ARCHITECTURE NOTE: This class encapsulates one specific behavior. 
Because it's a standalone file, it's easy to test and modify without 
impacting any other part of the system.
"""
class DefensiveFormation(Play):
    def execute(self):
        print("  [Action] Getting into defensive position…")