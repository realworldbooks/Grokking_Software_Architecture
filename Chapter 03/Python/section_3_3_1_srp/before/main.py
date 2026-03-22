from player import Player

if __name__ == "__main__":
    print("=== Chapter 3: SRP (BEFORE) ===")
    print("The Player class is doing way too much work!\n")

    player = Player("Alex")
    
    player.dribble_ball()
    player.determine_best_position()
    player.save_stats_to_database()

    print("\n===============================\n")