using System;

namespace Chapter03.LSP.After;

/// <summary>
/// The Confident Consumer.
/// 
/// ARCHITECTURE NOTE: Because we strictly adhered to LSP, the Coach class is 
/// incredibly simple and clean. 
/// 
/// Notice what is MISSING here: There are no "if (fieldPlayer is Goalie)" checks. 
/// There are no try/catch blocks expecting a NotImplementedException. The Coach 
/// trusts the abstraction 100%. If an object is passed in as a Player, the Coach 
/// knows for a fact it can play the field.
/// </summary>
public class Coach
{
    /// <summary>
    /// Directs the player to take their field position with absolute confidence.
    /// </summary>
    /// <param name="fieldPlayer">A guaranteed field-capable player.</param>
    public void DirectFieldPlay(Player fieldPlayer)
    {
        Console.WriteLine("  [Coach] Alright player, execute your field assignment!");
        
        // This will now ALWAYS succeed. No exceptions, no unexpected behavior.
        fieldPlayer.PlayFieldPosition();
    }
}