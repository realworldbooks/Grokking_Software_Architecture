from typing import Dict, Tuple, List
from option import Option

def pick_option(options: List[Option], weights: Dict[str, float]) -> Tuple[str, str]:
    """
    Implements a Weighted Decision Model to choose the best option from a set of choices.
    This model provides a quantitative and data-driven way to make architectural decisions.

    Args:
        options: A list of options to evaluate. Each option has scores for various criteria.
        weights: A dictionary where the key is the criterion name and the value is its importance (weight).

    Returns:
        A tuple containing the name of the best option and a string explaining the rationale.
    """
    best_option = None
    highest_score = float("-inf")
    details = []

    for opt in options:
        # THE CORE LOGIC: Calculate the weighted score.
        # Formula: FinalScore = (Score_A * Weight_A) + (Score_B * Weight_B) + ...
        score = sum(opt.scores.get(k, 0) * weights.get(k, 0.0) 
                    for k in weights)
        details.append(f"{opt.name}: {score:.2f}")

        if score > highest_score:
            highest_score = score
            best_option = opt

    # The rationale provides a transparent explanation for the decision.
    rationale = f"Scores: {' | '.join(details)}\\n -> Based on weights {weights}, we pick **{best_option.name}**."
    return best_option.name, rationale