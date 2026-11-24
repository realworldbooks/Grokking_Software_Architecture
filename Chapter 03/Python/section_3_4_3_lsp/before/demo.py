from .coach import Coach
from .goalie import Goalie

class Demo:
    @staticmethod
    def run():
        print("=== Chapter 3: LSP (BEFORE) ===")
        print("Passing a Goalie as a generic Player breaks the contract!\n")

        coach = Coach()
        goalie = Goalie()

        coach.direct_field_play(goalie)

        print("\n===============================\n")
