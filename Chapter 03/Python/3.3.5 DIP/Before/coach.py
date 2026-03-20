from forward import Forward
from midfielder import Midfielder

class Coach:
    def __init__(self):
        self.forward = Forward()
        self.midfielder = Midfielder()

    def execute_game_plan(self):
        self.forward.attack()
        self.midfielder.control_midfield()