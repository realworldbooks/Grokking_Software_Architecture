namespace Lsp.BadWay
{
    // The Parent Class
    public abstract class Player
    {
        // The contract: any subclass MUST behave like a field player.
        public abstract void PlayFieldPosition();
    }

    // A subclass that VIOLATES the contract
    public class Goalie : Player
    {
        public override void PlayFieldPosition()
        {
            Console.WriteLine("Stays near the net, uses hands.");
        }
    }
}