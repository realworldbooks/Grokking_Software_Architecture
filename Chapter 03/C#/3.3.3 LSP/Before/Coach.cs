using System;

namespace Chapter03.LSP.Before;

/// <summary>
/// The Consumer (The Victim of the LSP Violation).
/// 
/// ARCHITECTURE PROBLEM: The Coach is relying on the abstraction (Player). 
/// The Coach trusts that because the object is a Player, calling PlayFieldPosition() 
/// will work perfectly. Because the Goalie lied about its capabilities, the Coach's 
/// game plan is now broken.
/// </summary>
public class Coach
{
    /// <summary>
    /// Directs the player to take their field position.
    /// </summary>
    /// <param name="fieldPlayer">Any player (or so the Coach thinks).</param>
    public void DirectFieldPlay(Player fieldPlayer) 
    {
        Console.WriteLine("  [Coach] Alright player, execute your field assignment!");
        
        // If a Goalie is passed in here, the system breaks!
        fieldPlayer.PlayFieldPosition(); 
    } 
}