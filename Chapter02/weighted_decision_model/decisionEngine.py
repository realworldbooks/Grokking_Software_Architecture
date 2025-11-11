from dataclasses import dataclass
from typing import Dict, Tuple, List

# A simple structure to hold an option and its scores.
# Scores are from 1 (bad) to 5 (good) for each criterion.
@dataclass
class Option:
    name: str
    scores: Dict[str, int]  # e.g., {"speed": 5, "cost": 1, "simplicity": 4}

def pick_option(options: List[Option], weights: Dict[str, float]) -> Tuple[str, str]:
    """
    Picks the best option based on weighted scores.
    Returns the name of the best option and a rationale string.
    """
    best_option = None
    highest_score = float("-inf")
    details = []

    for opt in options:
        # Calculate the weighted score for this option
        score = sum(opt.scores.get(k, 0) * weights.get(k, 0.0)
                    for k in weights)
        details.append(f"{opt.name}: {score:.2f}")

        if score > highest_score:
            highest_score = score
            best_option = opt

    rationale = f"Scores: {' | '.join(details)}\n -> Based on weights {weights}, we pick **{best_option.name}**."
    return best_option.name, rationale

# --- To make it runnable ---

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