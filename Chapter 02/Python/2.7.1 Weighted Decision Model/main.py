from option import Option
from decision_maker import pick_option

def run_weighted_decision_model_demo():
    """
    Sets up and runs the demonstration of the weighted decision model.
    """
    print("--- Weighted Decision Model Example ---")

    # STEP 1: Define the architectural options and score them.
    # Score each on a scale of 1 (bad) to 5 (good) for each criterion.
    options = [
        Option("InMemory", {"availability": 1, "performance": 5, "simplicity": 5}),
        Option("Redis",    {"availability": 5, "performance": 4, "simplicity": 3}),
        Option("Database", {"availability": 4, "performance": 2, "simplicity": 4}),
    ]

    # ---
    # SCENARIO 1: The project's highest priority is high availability.
    # ---
    print("\\n[SCENARIO 1: Prioritizing Availability]")
    
    # STEP 2: Define the weights based on current priorities (should sum to 1.0).
    availability_focused_weights = {"availability": 0.6, "performance": 0.3, "simplicity": 0.1}

    # STEP 3: Run the model and get the decision.
    _, rationale_text_1 = pick_option(options, availability_focused_weights)
    print(rationale_text_1)

    # ---
    # SCENARIO 2: Priorities change. Now, performance and simplicity are key.
    # ---
    print("\\n[SCENARIO 2: Prioritizing Performance & Simplicity]")
    
    # STEP 2 (Re-run): Define a new set of weights.
    performance_focused_weights = {"availability": 0.1, "performance": 0.5, "simplicity": 0.4}
    
    # STEP 3 (Re-run): Get the new decision.
    _, rationale_text_2 = pick_option(options, performance_focused_weights)
    print(rationale_text_2)

    print("---------------------------------------\\n")

if __name__ == "__main__":
    run_weighted_decision_model_demo()