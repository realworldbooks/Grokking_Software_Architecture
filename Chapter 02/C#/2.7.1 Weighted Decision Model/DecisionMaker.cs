using System.Collections.Generic;
using System.Linq;

namespace Chapter02.WeightedDecisionModel;

public class DecisionMaker
{
    public (string BestOption, string Rationale) PickOption(List<Option> options, Dictionary<string, double> weights)
    {
        Option? bestOption = null;
        double highestScore = double.NegativeInfinity;
        var details = new List<string>();

        foreach (var opt in options)
        {
            // Calculate the weighted score for this option
            double score = weights.Sum(w => opt.Scores.GetValueOrDefault(w.Key, 0) * w.Value);
            details.Add($"{opt.Name}: {score:F2}");

            if (score > highestScore)
            {
                highestScore = score;
                bestOption = opt;
            }
        }

        // Formatting the weights to look similar to the Python dictionary output
        var weightsString = "{" + string.Join(", ", weights.Select(kv => $"'{kv.Key}': {kv.Value}")) + "}";
        string rationale = $"Scores: {string.Join(" | ", details)}\n -> Based on weights {weightsString}, we pick **{bestOption?.Name}**.";
        
        return (bestOption?.Name ?? "None", rationale);
    }
}