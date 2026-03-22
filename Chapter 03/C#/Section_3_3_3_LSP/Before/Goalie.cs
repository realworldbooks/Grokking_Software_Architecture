using System;

namespace Chapter03.LSP.Before;

/// <summary>
/// ANTI-PATTERN: Violation of the Liskov Substitution Principle (LSP).
/// 
/// ARCHITECTURE PROBLEM: LSP states that objects of a superclass should be 
/// replaceable with objects of its subclasses without breaking the application.
/// 
/// This Goalie class claims to be a "Player", but it refuses to fulfill the 
/// "PlayFieldPosition()" contract set by the base class. Often, developers will 
/// throw a NotImplementedException here. This means the Goalie CANNOT be safely 
/// substituted anywhere a Player is expected.
/// </summary>
public class Goalie : Player
{
    /// <summary>
    /// Breaks the base class contract by refusing to play the field.
    /// </summary>
    public override void PlayFieldPosition()
    {
        // 🚨 ARCHITECTURE WARNING: A goalie doesn't play the field! If the Coach 
        // calls this blindly, they get unexpected behavior or an outright crash.
        Console.WriteLine("  [Goalie] I can't do that! I stay near the net and use my hands.");
    }
}