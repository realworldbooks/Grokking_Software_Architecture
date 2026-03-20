class Midfielder:
    def execute_play(self, play_name):
        if play_name == "DribblePastOpponent":
            print("  [Action] Executing a dribble move…")
        elif play_name == "DefensiveFormation":
            print("  [Action] Getting into defensive position…")
        else:
            print(f"  [Error] Unknown play: {play_name}")