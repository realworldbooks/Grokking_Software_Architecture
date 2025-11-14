namespace Dip.GoodWay
{
    // High-level module (also depends on abstractions)
    public class Coach
    {
        private readonly List<IPlayer> _team;

        // Dependencies are "injected" via the constructor!
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
}