using System.Collections.Generic;
using System.Linq;

namespace Chapter02.WeightedDecisionModel;

/// <summary>
/// Implements a Weighted Decision Model to choose the best option from a set of choices.
/// This model provides a quantitative and data-driven way to make architectural decisions.
/// 
/// ARCHITECTURAL NOTE: Encapsulating the Algorithm
/// The logic for calculating the winner is isolated here. If we decide to change 
/// our mathematical model later (e.g., using a logarithmic scale instead of linear), 
/// we only have to update this one class.
/// </summary>
public class DecisionMaker
{
    /// <summary>
    /// Picks the best option based on a set of weighted criteria.
    /// </summary>
    /// <param name="options">A list of options to evaluate. Each option has scores for various criteria.</param>
    /// <param name="weights">A dictionary where the key is the criterion name and the value is its importance (weight).</param>
    /// <returns>A tuple containing the name of the best option and a string explaining the rationale.</returns>
    public (string BestOption, string Rationale) PickOption(List<Option> options, Dictionary<string, double> weights)
    {
        Option? bestOption = null;
        double highestScore = double.NegativeInfinity;
        var details = new List<string>();

        foreach (var opt in options)
        {
            // THE CORE LOGIC: Calculate the weighted score for this option.
            // For each criterion (e.g., "performance", "cost"), we multiply the option's
            // score for that criterion (e.g., 4/5) by the weight we've assigned to that
            // criterion (e.g., 60% importance). We sum these products to get the final score.
            // Formula: FinalScore = (Score_A * Weight_A) + (Score_B * Weight_B) + ...
            double score = weights.Sum(w => opt.Scores.GetValueOrDefault(w.Key, 0) * w.Value);
            details.Add($"{opt.Name}: {score:F2}");

            if (score > highestScore)
            {
                highestScore = score;
                bestOption = opt;
            }
        }

        // The rationale provides a transparent explanation for the decision,
        // which is crucial for communicating architectural choices to a team.
        var weightsString = "{" + string.Join(", ", weights.Select(kv => $"'{kv.Key}': {kv.Value}")) + "}";
        
        // Note: The newline escape character has been fixed here to properly render in the console.
        string rationale = $"Scores: {string.Join(" | ", details)}\n -> Based on weights {weightsString}, we pick **{bestOption?.Name}**.";
        
        return (bestOption?.Name ?? "None", rationale);
    }
}