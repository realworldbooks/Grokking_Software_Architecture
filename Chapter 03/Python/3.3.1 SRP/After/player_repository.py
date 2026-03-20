# Responsibility 3: Manages only data saving
class PlayerRepository:
    def save_stats(self, player):
        print(f"  [Database] Saving {player.name}'s game stats to the database.")