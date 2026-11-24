using System.Collections.Generic;

namespace Chapter03.DIP.After;

/// <summary>
/// DIP SOLUTION: Constructor Injection and Loose Coupling.
/// 
/// ARCHITECTURE NOTE: The Coach is no longer responsible for building its own 
/// team. The 'new' keyword has been completely removed! 
/// 
/// Instead, the Coach asks for its dependencies through its constructor. This 
/// is called "Dependency Injection." Because the Coach only asks for a list 
/// of 'IPlayer' abstractions, we can hand it literally any combination of players. 
/// 
/// This makes the Coach class infinitely extensible and incredibly easy to unit 
/// test (we can just pass in "Mock" players during testing).
/// </summary>
public class Coach
{
    // The Coach depends strictly on an abstraction!
    private readonly List<IPlayer> _team;

    /// <summary>
    /// Dependencies are provided from the outside (Constructor Injection).
    /// </summary>
    public Coach(List<IPlayer> players)
    {
        _team = players;
    }

    public void ExecuteGamePlan()
    {
        foreach (var player in _team)
        {
            player.PerformAction();
        }
    }
}