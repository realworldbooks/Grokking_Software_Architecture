using System.Collections.Generic;

namespace Chapter03.DIP.After;

public class Coach
{
    // The Coach depends on an abstraction!
    private readonly List<IPlayer> _team;

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