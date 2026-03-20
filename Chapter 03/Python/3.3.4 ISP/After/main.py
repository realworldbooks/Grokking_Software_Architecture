from midfielder import Midfielder
from goalie import Goalie

if __name__ == "__main__":
    print("=== Chapter 3: ISP (AFTER) ===")
    print("Interfaces are segregated. No more NotImplementedErrors!\n")

    midfielder = Midfielder()
    midfielder.practice_shooting()
    
    print()
    
    goalie = Goalie()
    goalie.practice_diving_saves()
    goalie.practice_hand_distribution()

    print("\n===============================\n")