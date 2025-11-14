namespace Srp.GoodWay
{
    // Responsibility 3: Manages only data saving
    public class PlayerRepository
    {
        public void SaveStats(Player player) 
        {
            Console.WriteLine($"Saving stats for {player.Name} to database...");
        }
    }
}