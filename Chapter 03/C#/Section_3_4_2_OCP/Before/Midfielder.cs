using System;

namespace Chapter03.OCP.Before;

/// <summary>
/// ANTI-PATTERN: Violation of the Open/Closed Principle (OCP).
/// 
/// ARCHITECTURE PROBLEM: The Open/Closed Principle states that a class should be 
/// "Open for extension, but Closed for modification." 
/// 
/// Right now, this class is heavily modified every time requirements change. If 
/// the team learns a new play (like "PassToStriker"), we are FORCED to open this 
/// file, modify the Midfielder class, and add another 'else if' block. 
/// 
/// This means every new feature requires altering existing, already-tested code, 
/// which dramatically increases the risk of introducing regressions or bugs.
/// </summary>
public class Midfielder
{
    /// <summary>
    /// Executes a play based on a hardcoded string. 
    /// This creates a fragile, infinitely growing conditional chain.
    /// </summary>
    /// <param name="playName">The string identifier of the play.</param>
    public void ExecutePlay(string playName)
    {
        // 🚨 ARCHITECTURE WARNING: This if/else chain will grow forever.
        if (playName == "DribblePastOpponent") 
        {
            Console.WriteLine("  [Action] Executing a dribble move…");
        }
        else if (playName == "DefensiveFormation")
        {
            Console.WriteLine("  [Action] Getting into defensive position…");
        }
        else 
        {
            Console.WriteLine($"  [Error] Unknown play: {playName}");
        }
    }
}