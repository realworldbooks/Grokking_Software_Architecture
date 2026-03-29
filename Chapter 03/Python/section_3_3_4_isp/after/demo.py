from midfielder import Midfielder
from goalie import Goalie

class Demo:
    @staticmethod
    def run():
        print("=== Chapter 3: ISP (AFTER) ===")
        print("Interfaces are segregated. No more NotImplementedErrors!\n")

        # This object is clean and focused.
        midfielder = Midfielder()
        midfielder.practice_shooting()
        
        print()
        
        # This object is specialized but still fulfills the field contract.
        goalie = Goalie()
        goalie.practice_diving_saves()
        goalie.practice_hand_distribution()

        print("\n===============================\n")

