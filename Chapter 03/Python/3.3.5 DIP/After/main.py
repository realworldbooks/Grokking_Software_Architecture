from coach import Coach
from forward import Forward
from midfielder import Midfielder
from winger import Winger

if __name__ == "__main__":
    print("=== Chapter 3: DIP (AFTER) ===")
    print("The Coach depends on the Player abstraction, allowing for easy team changes!\n")

    team = [
        Forward(),
        Midfielder(),
        Winger()
    ]

    coach = Coach(team)
    coach.execute_game_plan()

    print("\n===============================\n")