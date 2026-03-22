from coach import Coach
from goalie import Goalie

if __name__ == "__main__":
    print("=== Chapter 3: LSP (BEFORE) ===")
    print("Passing a Goalie as a generic Player breaks the contract!\n")

    coach = Coach()
    goalie = Goalie()

    coach.direct_field_play(goalie)

    print("\n===============================\n")