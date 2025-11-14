using System;

namespace Srp.GoodWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var player = new Player { Name = "Archie" };
            var tactics = new TacticsEngine();
            var repo = new PlayerRepository();

            player.DribbleBall();
            tactics.DeterminesBestPosition(player);
            repo.SaveStats(player);
            Console.WriteLine("Ran Good Way SRP example.");
        }
    }
}