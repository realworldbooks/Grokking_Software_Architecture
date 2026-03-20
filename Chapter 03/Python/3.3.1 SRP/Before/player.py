class Player:
    def __init__(self, name):
        self.name = name

    # Responsibility 1: Player’s own state/abilities
    def dribble_ball(self):
        print(f"  [Action] {self.name} is dribbling the ball down the court.")

    # Responsibility 2: Tactical Logic
    def determine_best_position(self):
        print(f"  [Tactics] Calculating optimal court position for {self.name}...")

    # Responsibility 3: Data Persistence
    def save_stats_to_database(self):
        print(f"  [Database] Saving {self.name}'s game stats to the database.")