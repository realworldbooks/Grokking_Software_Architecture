using System;
using System.Collections.Generic;

namespace Chapter02.WeightedDecisionModel;

public static class WeightedDecisionModelDemo
{
    public static void Run()
    {
        Console.WriteLine("--- Weighted Decision Model Example ---");

        // STEP 1: Define the architectural options and score them.
        // As a team, you would evaluate each option against a set of criteria that
        // are important to your project. Here, we're deciding on a caching strategy.
        // We score each option on a scale of 1 (bad) to 5 (good) for each criterion.
        var options = new List<Option>
        {
            new() { Name = "InMemory", Scores = new() { {"availability", 1}, {"performance", 5}, {"simplicity", 5} } },
            new() { Name = "Redis",    Scores = new() { {"availability", 5}, {"performance", 4}, {"simplicity", 3} } },
            new() { Name = "Database", Scores = new() { {"availability", 4}, {"performance", 2}, {"simplicity", 4} } }
        };

        var decisionMaker = new DecisionMaker();

        // ---
        // SCENARIO 1: The project's highest priority is high availability.
        // ---
        Console.WriteLine("\\n[SCENARIO 1: Prioritizing Availability]");
        
        // STEP 2: Define the weights based on current priorities.
        // The weights represent the relative importance of each criterion. They should sum to 1.0.
        // Here, "availability" is paramount, so it gets a high weight of 0.6 (or 60%).
        var availabilityFocusedWeights = new Dictionary<string, double> { {"availability", 0.6}, {"performance", 0.3}, {"simplicity", 0.1} };
        
        // STEP 3: Run the model and get the decision.
        var (_, rationale1) = decisionMaker.PickOption(options, availabilityFocusedWeights);
        Console.WriteLine(rationale1);
        // With these weights, Redis is the clear winner because of its high availability score.

        // ---
        // SCENARIO 2: Project priorities change. Now, raw performance and simplicity are key.
        // ---
        Console.WriteLine("\\n[SCENARIO 2: Prioritizing Performance & Simplicity]");
        
        // STEP 2 (Re-run): Define a new set of weights reflecting the new priorities.
        var performanceFocusedWeights = new Dictionary<string, double> { {"availability", 0.1}, {"performance", 0.5}, {"simplicity", 0.4} };
        
        // STEP 3 (Re-run): Get the new decision.
        var (_, rationale2) = decisionMaker.PickOption(options, performanceFocusedWeights);
        Console.WriteLine(rationale2);
        // By simply changing the weights, the model now recommends the InMemory option,
        // demonstrating how this tool can adapt to different project needs.

        Console.WriteLine("---------------------------------------\\n");
    }
}