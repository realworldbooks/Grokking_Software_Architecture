using System.Collections.Generic;

namespace Chapter02.WeightedDecisionModel;

/// <summary>
/// Represents a single architectural choice to be evaluated.
/// This is a simple data-holding class (POCO).
/// 
/// ARCHITECTURAL NOTE: Data Structures
/// By keeping this class purely for data, we can easily serialize it, 
/// deserialize it from a database, or pass it around without dragging 
/// any heavy calculation logic along with it.
/// </summary>
public class Option
{
    /// <summary>
    /// The name of the architectural option (e.g., "Redis", "In-Memory Cache").
    /// </summary>
    public required string Name { get; set; }

    /// <summary>
    /// A dictionary holding the scores for this option against various criteria.
    /// The key is the criterion name (e.g., "performance", "cost") and the value
    /// is the score, typically on a scale (e.g., 1 to 5).
    /// </summary>
    public required Dictionary<string, int> Scores { get; set; }
}