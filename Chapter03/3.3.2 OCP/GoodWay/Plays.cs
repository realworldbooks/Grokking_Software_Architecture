namespace Ocp.GoodWay
{
    // Extension 1: A new class for a dribble move
    public class DribblePastOpponent : IPlay
    {
        public void Execute()
        {
            Console.WriteLine("Executing a dribble move...");
        }
    }

    // Extension 2: A new class for a defensive formation
    public class DefensiveFormation : IPlay
    {
        public void Execute()
        {
            Console.WriteLine("Getting into defensive position...");
        }
    }
}