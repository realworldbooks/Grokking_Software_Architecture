from option import Option
from decision_maker import pick_option

if __name__ == "__main__":
    print("--- Running Weighted Decision Model Example ---")

    # 1. Define our options and score them from 1 (worst) to 5 (best)
    options = [
        Option("InMemory", {"availability": 1, "performance": 5, "simplicity": 5}),
        Option("Redis",    {"availability": 5, "performance": 4, "simplicity": 3}),
        Option("Database", {"availability": 4, "performance": 2, "simplicity": 4}),
    ]

    # 2. Define our priorities: Availability is most important (60%).
    print("\nScenario 1: Prioritizing Availability")
    our_priorities = {"availability": 0.6, "performance": 0.3, "simplicity": 0.1}

    # 3. Get the decision!
    decision, rationale_text = pick_option(options, our_priorities)
    print(rationale_text)

    # 4. Define new priorities: Performance and Simplicity are most important.
    print("\nScenario 2: Prioritizing Performance & Simplicity")
    new_priorities = {"availability": 0.1, "performance": 0.5, "simplicity": 0.4}
    decision, rationale_text = pick_option(options, new_priorities)
    print(rationale_text)

    print("-----------------------------------------------")