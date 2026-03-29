"""
Implements a Weighted Decision Model to choose the best option from a set of choices.
ARCHITECTURAL NOTE: Encapsulating the Algorithm
The logic for calculating the winner is isolated here. This provides 
a quantitative and data-driven way to make architectural decisions.
"""

class DecisionMaker:
    def pick_option(self, options: list['Option'], weights: dict[str, float]) -> tuple[str, str]:
        """
        Picks the best option based on a set of weighted criteria.
        
        Formula: FinalScore = sum(Score_i * Weight_i)
        """
        best_option = None
        highest_score = float('-inf')
        details = []

        for opt in options:
            # THE CORE LOGIC: Weighted sum calculation
            # We multiply each criterion score by its weight and sum them up.
            score = sum(opt.scores.get(criterion, 0) * weight 
                        for criterion, weight in weights.items())
            
            details.append(f"{opt.name}: {score:.2f}")

            if score > highest_score:
                highest_score = score
                best_option = opt

        # The rationale provides transparency for communicating the choice to the team.
        weights_str = str(weights)
        best_name = best_option.name if best_option else "None"
        
        rationale = (f"Scores: {' | '.join(details)}\n"
                     f" -> Based on weights {weights_str}, we pick **{best_name}**.")
        
        return best_name, rationale