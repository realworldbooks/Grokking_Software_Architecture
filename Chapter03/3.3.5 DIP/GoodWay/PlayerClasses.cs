namespace Dip.GoodWay
{
    // Low-level modules (depend on the abstraction)
    public class Forward : IPlayer
    {
        public void PerformAction() => Console.WriteLine("Forward is attacking.");
    }

    public class Midfielder : IPlayer
    {
        public void PerformAction() => Console.WriteLine("Midfielder is controlling the game.");
    }

    public class Winger : IPlayer
    {
        public void PerformAction() => Console.WriteLine("Winger is running down the sideline.");
    }
}