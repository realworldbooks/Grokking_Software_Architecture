using System;
using System.Collections.Generic;

namespace Chapter02.WeightedDecisionModel;

class Program
{
    static void Main()
    {
        Console.WriteLine("--- Running Weighted Decision Model Example ---");

        // 1. Define our options and score them from 1 (worst) to 5 (best)
        var options = new List<Option>
        {
            new Option { Name = "InMemory", Scores = new Dictionary<string, int> { {"availability", 1}, {"performance", 5}, {"simplicity", 5} } },
            new Option { Name = "Redis",    Scores = new Dictionary<string, int> { {"availability", 5}, {"performance", 4}, {"simplicity", 3} } },
            new Option { Name = "Database", Scores = new Dictionary<string, int> { {"availability", 4}, {"performance", 2}, {"simplicity", 4} } }
        };

        var decisionMaker = new DecisionMaker();

        // 2. Define our priorities: Availability is most important (60%).
        Console.WriteLine("\nScenario 1: Prioritizing Availability");
        var ourPriorities = new Dictionary<string, double> { {"availability", 0.6}, {"performance", 0.3}, {"simplicity", 0.1} };
        
        // 3. Get the decision!
        var (decision1, rationaleText1) = decisionMaker.PickOption(options, ourPriorities);
        Console.WriteLine(rationaleText1);

        // 4. Define new priorities: Performance and Simplicity are most important.
        Console.WriteLine("\nScenario 2: Prioritizing Performance & Simplicity");
        var newPriorities = new Dictionary<string, double> { {"availability", 0.1}, {"performance", 0.5}, {"simplicity", 0.4} };
        
        var (decision2, rationaleText2) = decisionMaker.PickOption(options, newPriorities);
        Console.WriteLine(rationaleText2);

        Console.WriteLine("-----------------------------------------------");
    }
}