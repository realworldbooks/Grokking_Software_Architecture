from midfielder import Midfielder

if __name__ == "__main__":
    print("=== Chapter 3: ISP (BEFORE) ===")
    print("Midfielder is forced to implement Goalie methods!\n")

    player = Midfielder()
    player.practice_shooting()
    player.practice_tackling()

    try:
        player.practice_diving_saves() # This will crash!
    except NotImplementedError as e:
        print(f"  [ERROR] {e}")

    print("\n===============================\n")