using System;

namespace Chapter03.ISP.After;

/// <summary>
/// The Clean Implementation.
/// 
/// ARCHITECTURE NOTE: Look at how clean this class is now! There are no 
/// NotImplementedExceptions. There is no dead code. The Midfielder simply 
/// signs the IFieldPlayerTraining contract and easily fulfills all of its 
/// obligations. The class is now highly cohesive.
/// </summary>
public class Midfielder : IFieldPlayerTraining
{
    /// <summary>
    /// A valid method for this specific class.
    /// </summary>
    public void PracticeShooting() 
    { 
        Console.WriteLine("  [Midfielder] Practicing shooting drills."); 
    }
    
    /// <summary>
    /// A valid method for this specific class.
    /// </summary>
    public void PracticeTackling() 
    { 
        Console.WriteLine("  [Midfielder] Practicing slide tackles."); 
    }
}