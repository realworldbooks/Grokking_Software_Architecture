from typing import Dict, Tuple, List
from option import Option

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