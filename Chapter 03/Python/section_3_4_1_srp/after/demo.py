from .player import Player
from .tactics_engine import TacticsEngine
from .player_repository import PlayerRepository

class Demo:
    @staticmethod
    def run():
        print("=== Chapter 3: SRP (AFTER) ===")
        print("Responsibilities are cleanly delegated to specific classes!\n")

        player = Player("Alex")
        tactics = TacticsEngine()
        repository = PlayerRepository()
        
        player.dribble_ball()
        tactics.determine_best_position(player)
        repository.save_stats(player)

        print("\n===============================\n")


