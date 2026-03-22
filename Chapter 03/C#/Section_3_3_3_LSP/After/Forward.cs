using System;

namespace Chapter03.LSP.After;

/// <summary>
/// LSP SOLUTION: True Substitutability.
/// 
/// ARCHITECTURE NOTE: A Forward is a true substitute for a Player. It fully 
/// honors the contract set by the base class. It doesn't throw a 
/// NotImplementedException or refuse to do the work.
/// </summary>
public class Forward : Player 
{
    /// <summary>
    /// Flawlessly fulfills the base class contract.
    /// </summary>
    public override void PlayFieldPosition() 
    {
        Console.WriteLine("  [Forward] Leading the attack and trying to score."); 
    } 
}