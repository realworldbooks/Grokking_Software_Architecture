/**
 * Implements a Weighted Decision Model to choose the best option from a set of choices.
 * This model provides a quantitative and data-driven way to make architectural decisions.
 * 
 * @param {Array<Option>} options - A list of options to evaluate. Each option has scores for various criteria.
 * @param {Object.<string, number>} weights - An object where the key is the criterion name and the value is its importance (weight).
 * @returns {{bestOption: string, rationale: string}} An object containing the name of the best option and a string explaining the rationale.
 */
function pickOption(options, weights) {
    let bestOption = null;
    let highestScore = -Infinity;
    const details = [];

    for (const opt of options) {
        // THE CORE LOGIC: Calculate the weighted score for this option.
        // For each criterion (e.g., "performance"), we multiply the option's
        // score for that criterion (e.g., 4/5) by the weight we've assigned to that
        // criterion (e.g., 60% importance) and sum the results.
        // Formula: FinalScore = (Score_A * Weight_A) + (Score_B * Weight_B) + ...
        let score = 0;
        for (const [key, weight] of Object.entries(weights)) {
            score += (opt.scores[key] || 0) * weight;
        }
        details.push(`${opt.name}: ${score.toFixed(2)}`);

        if (score > highestScore) {
            highestScore = score;
            bestOption = opt;
        }
    }

    // The rationale provides a transparent explanation for the decision.
    const weightsString = JSON.stringify(weights).replace(/"/g, "'");
    const rationale = `Scores: ${details.join(' | ')}\\n -> Based on weights ${weightsString}, we pick **${bestOption.name}**.`;
    
    return { bestOption: bestOption.name, rationale };
}

module.exports = { pickOption };