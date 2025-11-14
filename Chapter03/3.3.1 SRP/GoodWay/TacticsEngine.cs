namespace Srp.GoodWay
{
    // Responsibility 2: Manages only tactical decisions
    public class TacticsEngine
    {
        public void DeterminesBestPosition(Player player) 
        {
            Console.WriteLine($"Determining best position for {player.Name}...");
        }
    }
}