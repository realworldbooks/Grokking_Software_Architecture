from coach import Coach

if __name__ == "__main__":
    print("=== Chapter 3: DIP (BEFORE) ===")
    print("The Coach is tightly coupled to concrete players.\n")

    coach = Coach()
    coach.execute_game_plan()

    print("\n===============================\n")