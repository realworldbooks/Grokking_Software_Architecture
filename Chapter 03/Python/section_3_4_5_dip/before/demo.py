from .coach import Coach

class Demo:
    @staticmethod
    def run():
        print("=== Chapter 3: DIP (BEFORE) ===")
        print("The Coach is tightly coupled to concrete players.\n")

        coach = Coach()
        coach.execute_game_plan()

        print("\n===============================\n")

