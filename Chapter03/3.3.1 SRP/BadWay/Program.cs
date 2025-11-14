using System;

namespace Srp.BadWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var player = new Player { Name = "Archie" };
            player.DribbleBall();
            player.DetermineBestPosition();
            player.SaveStatsToDatabase();
            Console.WriteLine("Ran Bad Way SRP example.");
        }
    }
}