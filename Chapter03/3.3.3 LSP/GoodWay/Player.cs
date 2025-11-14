namespace Lsp.GoodWay
{
    // The Parent Class (same as before)
    public abstract class Player
    {
        public abstract void PlayFieldPosition();
    }

    // A "Good Subclass" that HONORS the contract
    public class Midfielder : Player
    {
        public override void PlayFieldPosition()
        {
            Console.WriteLine("Controlling the midfield, passing and tackling.");
        }
    }

    // Another "Good Subclass"
    public class Forward : Player
    {
        public override void PlayFieldPosition()
        {
            Console.WriteLine("Leading the attack and trying to score.");
        }
    }
}