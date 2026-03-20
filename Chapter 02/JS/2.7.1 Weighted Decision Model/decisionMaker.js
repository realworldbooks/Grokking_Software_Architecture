/**
 * Picks the best option based on weighted scores.
 * Returns an object containing the name of the best option and a rationale string.
 */
function pickOption(options, weights) {
    let bestOption = null;
    let highestScore = -Infinity;
    const details = [];

    for (const opt of options) {
        // Calculate the weighted score for this option
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

    // JSON stringify the weights and clean up the quotes to roughly match the Python output
    const weightsString = JSON.stringify(weights).replace(/"/g, "'");
    const rationale = `Scores: ${details.join(' | ')}\n -> Based on weights ${weightsString}, we pick **${bestOption.name}**.`;
    
    return { bestOption: bestOption.name, rationale };
}

module.exports = { pickOption };