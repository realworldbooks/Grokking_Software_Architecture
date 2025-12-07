namespace Srp.Before
{
    public class Player
    {
        public string Name { get; set; }

        // Responsibility 1: Player’s own state/abilities
        public void DribbleBall() { Console.WriteLine("Dribbling ball..."); }

        // Responsibility 2: Tactical Logic
        public void DetermineBestPosition()
        {
            Console.WriteLine("Determining best position...");
        }

        // Responsibility 3: Data Persistence
        public void SaveStatsToDatabase()
        {
            Console.WriteLine("Saving stats to database...");
        }
    }
}