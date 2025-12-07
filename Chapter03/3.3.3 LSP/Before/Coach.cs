namespace Lsp.Before
{
    // The Application
    public class Coach
    {
        public void DirectFieldPlay(Player fieldPlayer)
        {
            Console.WriteLine("Coach: 'Go play your field position!'");
            fieldPlayer.PlayFieldPosition();
        }
    }
}