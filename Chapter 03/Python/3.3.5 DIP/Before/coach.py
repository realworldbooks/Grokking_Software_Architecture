from forward import Forward
from midfielder import Midfielder

"""
ANTI-PATTERN: Violation of the Dependency Inversion Principle (DIP).

ARCHITECTURE PROBLEM: High-level modules (Coach) should not depend on 
low-level modules (Forward/Midfielder). Both should depend on abstractions.

By using 'import' and 'self.player = Player()' inside this class, the Coach 
is now "hard-wired" to these specific implementations.

WHY THIS IS BRITTLE:
- Rigidity: If you want to swap a Midfielder for a Winger, you have to 
  manually edit the Coach class.
- Untestable: You cannot unit test the Coach in isolation. You are forced 
  to create real instances of the players every time.
- Violation of "New is Glue": The Coach is responsible for creating its 
  own team instead of just leading the team it's given.
"""

class Coach:
    def __init__(self):
        # 🚨 ARCHITECTURE WARNING: Direct Instantiation.
        # The Coach is now stuck with these specific classes forever.
        self.forward = Forward()
        self.midfielder = Midfielder()

    def execute_game_plan(self):
        # 🚨 ARCHITECTURE WARNING: Specific Method Coupling.
        # The Coach has to know the exact method names (attack, control_midfield)
        # for each specific player type.
        self.forward.attack()
        self.midfielder.control_midfield()