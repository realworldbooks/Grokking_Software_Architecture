namespace Lsp.GoodWay
{
    // The Application (same as before)
    public class Coach
    {
        public void DirectFieldPlay(Player fieldPlayer)
        {
            Console.WriteLine("Coach: 'Go play your field position!'");
            fieldPlayer.PlayFieldPosition();
        }
    }
}