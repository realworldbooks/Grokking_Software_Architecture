/**
 * Implements a Weighted Decision Model to choose the best option from a set of choices.
 * This model provides a quantitative and data-driven way to make architectural decisions.
 * * ARCHITECTURAL NOTE: Encapsulating the Algorithm
 * The logic for calculating the winner is isolated here. If we decide to change 
 * our mathematical model later, we only have to update this one class.
 */
class DecisionMaker {
    /**
     * Picks the best option based on a set of weighted criteria.
     * @param {Option[]} options - A list of options to evaluate.
     * @param {Object.<string, number>} weights - Criterion names and their weights (summing to 1.0).
     * @returns {{bestOption: string, rationale: string}}
     */
    pickOption(options, weights) {
        let bestOption = null;
        let highestScore = -Infinity;
        const details = [];

        for (const opt of options) {
            // THE CORE LOGIC: Calculate the weighted score.
            // Formula: FinalScore = sum(Score_i * Weight_i)
            const score = Object.keys(weights).reduce((total, criterion) => {
                const optionScore = opt.scores[criterion] || 0;
                const weight = weights[criterion];
                return total + (optionScore * weight);
            }, 0);

            details.push(`${opt.name}: ${score.toFixed(2)}`);

            if (score > highestScore) {
                highestScore = score;
                bestOption = opt;
            }
        }

        // The rationale provides a transparent explanation for the decision.
        const weightsString = JSON.stringify(weights).replace(/"/g, "'");
        const bestName = bestOption ? bestOption.name : "None";
        
        const rationale = `Scores: ${details.join(" | ")}\n -> Based on weights ${weightsString}, we pick **${bestName}**.`;
        
        return { bestOption: bestName, rationale };
    }
}

module.exports = DecisionMaker;