from .option import Option
from .decision_maker import DecisionMaker

class Demo:
    """
    Demonstrates how to configure and execute the Weighted Decision Model.
    """

    @staticmethod
    def run():
        print("--- Weighted Decision Model Example ---")

        # STEP 1: Define the architectural options and score them (1 to 5).
        options = [
            Option("InMemory", {"availability": 1, "performance": 5, "simplicity": 5}),
            Option("Redis",    {"availability": 5, "performance": 4, "simplicity": 3}),
            Option("Database", {"availability": 4, "performance": 2, "simplicity": 4})
        ]

        decision_maker = DecisionMaker()

        # SCENARIO 1: High Availability is the priority.
        print("\n[SCENARIO 1: Prioritizing Availability]")
        availability_weights = {"availability": 0.6, "performance": 0.3, "simplicity": 0.1}
        _, rationale1 = decision_maker.pick_option(options, availability_weights)
        print(rationale1)

        # SCENARIO 2: Performance and Simplicity are the priorities.
        print("\n[SCENARIO 2: Prioritizing Performance & Simplicity]")
        performance_weights = {"availability": 0.1, "performance": 0.5, "simplicity": 0.4}
        _, rationale2 = decision_maker.pick_option(options, performance_weights)
        print(rationale2)

        print("---------------------------------------\n")