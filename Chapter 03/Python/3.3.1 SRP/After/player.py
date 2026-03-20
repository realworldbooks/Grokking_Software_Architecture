# Responsibility 1: Manages only the player’s state and actions
class Player:
    def __init__(self, name):
        self.name = name

    def dribble_ball(self):
        print(f"  [Action] {self.name} is dribbling the ball down the court.")