using System;

namespace Chapter03.LSP.After;

public class Coach
{
    public void DirectFieldPlay(Player fieldPlayer)
    {
        Console.WriteLine("  [Coach] Alright player, execute your field assignment!");
        fieldPlayer.PlayFieldPosition();
    }
}