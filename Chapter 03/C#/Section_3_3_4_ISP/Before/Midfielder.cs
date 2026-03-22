using System;

namespace Chapter03.ISP.Before;

/// <summary>
/// The Polluted Implementation (The Victim of the ISP Violation).
/// 
/// ARCHITECTURE PROBLEM: Because the Midfielder wants to participate in 
/// the ITrainingSession, it is FORCED by the compiler to implement methods 
/// it has no business knowing about. 
/// 
/// To make the compiler happy, the developer has to write "dummy" methods 
/// or throw NotImplementedExceptions. This creates "code rot" and sets traps 
/// for other developers who might accidentally call these methods expecting 
/// them to actually do something.
/// </summary>
public class Midfielder : ITrainingSession
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
    
    /// <summary>
    /// 🚨 ARCHITECTURE WARNING: The Midfielder doesn't need this, but the 
    /// Fat Interface demands it! We are forced to throw an exception.
    /// </summary>
    public void PracticeDivingSaves() 
    {
        throw new NotImplementedException("Midfielders don't play in the net!");
    }
    
    /// <summary>
    /// 🚨 ARCHITECTURE WARNING: Another useless method forced upon us.
    /// </summary>
    public void PracticeHandDistribution() 
    {
        throw new NotImplementedException("Midfielders can't use their hands!");
    }
}