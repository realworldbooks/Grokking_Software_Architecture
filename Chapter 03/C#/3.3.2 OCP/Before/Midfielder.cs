using System;

namespace Chapter03.OCP.Before;

public class Midfielder
{
    public void ExecutePlay(string playName)
    {
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