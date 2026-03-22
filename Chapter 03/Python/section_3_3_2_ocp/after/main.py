from midfielder import Midfielder
from dribble_past_opponent import DribblePastOpponent
from defensive_formation import DefensiveFormation
from pass_to_striker import PassToStriker

if __name__ == "__main__":
    print("=== Chapter 3: OCP (AFTER) ===")
    print("Midfielder accepts any class inheriting from Play!\n")

    midfielder = Midfielder()
    
    midfielder.execute_play(DribblePastOpponent())
    midfielder.execute_play(DefensiveFormation())
    midfielder.execute_play(PassToStriker()) # Success!

    print("\n===============================\n")