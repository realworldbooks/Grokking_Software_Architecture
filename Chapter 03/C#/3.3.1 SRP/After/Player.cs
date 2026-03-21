using System;

namespace Chapter03.SRP.After;

/// <summary>
/// Responsibility 1: State and Core Actions.
/// 
/// ARCHITECTURE NOTE: By stripping out the database and tactical logic, the Player 
/// class is now highly cohesive. It has only one reason to change: if the fundamental 
/// rules of a player (like adding a 'Pass' method or 'Stamina' property) change.
/// </summary>
public class Player
{
    public required string Name { get; set; }
    
    /// <summary>
    /// Executes a core domain action specific to the player's physical state.
    /// </summary>
    public void DribbleBall() 
    {
        Console.WriteLine($"  [Action] {Name} is dribbling the ball down the court.");
    }
}