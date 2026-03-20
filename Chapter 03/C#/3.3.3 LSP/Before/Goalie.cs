using System;

namespace Chapter03.LSP.Before;

public class Goalie : Player
{
    public override void PlayFieldPosition()
    {
        // VIOLATION: A goalie doesn't play the field. If the Coach calls this, 
        // they get unexpected behavior (or an exception)!
        Console.WriteLine("  [Goalie] I can't do that! I stay near the net and use my hands.");
    }
}