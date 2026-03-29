from midfielder import Midfielder

class Demo:
    @staticmethod
    def run():
        print("=== Chapter 3: OCP (BEFORE) ===")
        print("Midfielder uses hardcoded if/else logic for plays.\n")

        midfielder = Midfielder()
        midfielder.execute_play("DribblePastOpponent")
        midfielder.execute_play("DefensiveFormation")
        midfielder.execute_play("PassToStriker") # Fails!

        print("\n===============================\n")

