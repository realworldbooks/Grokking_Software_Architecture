using System;

namespace Chapter03.SRP.After;

// Responsibility 1: Manages only the player’s state and actions
public class Player
{
    public required string Name { get; set; }
    
    public void DribbleBall() 
    {
        Console.WriteLine($"  [Action] {Name} is dribbling the ball down the court.");
    }
}