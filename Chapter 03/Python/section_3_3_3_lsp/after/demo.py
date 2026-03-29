from .coach import Coach
from .forward import Forward
from .midfielder import Midfielder

class Demo:
    @staticmethod
    def run():
        print("=== Chapter 3: LSP (AFTER) ===")
        print("The Coach can direct any Player type without issue!\n")

        coach = Coach()
        forward = Forward()
        midfielder = Midfielder()

        coach.direct_field_play(forward)
        coach.direct_field_play(midfielder)

        print("\n===============================\n")

