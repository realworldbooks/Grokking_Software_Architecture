using System;

namespace Chapter03.LSP.After;

/// <summary>
/// LSP SOLUTION: True Substitutability.
/// 
/// ARCHITECTURE NOTE: Just like the Forward, the Midfielder fully supports the 
/// behavior expected of a generic Player. We can swap a Forward for a Midfielder 
/// at runtime, and the application will remain perfectly stable.
/// </summary>
public class Midfielder : Player 
{
    /// <summary>
    /// Flawlessly fulfills the base class contract.
    /// </summary>
    public override void PlayFieldPosition() 
    {
        Console.WriteLine("  [Midfielder] Controlling the midfield, passing and tackling."); 
    } 
}