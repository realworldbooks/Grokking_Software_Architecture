using System;

namespace Lsp.BadWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var coach = new Coach();
            Console.WriteLine("Directing field play with a Goalie:");
            // This will run, but the behavior is wrong!
            coach.DirectFieldPlay(new Goalie());
            Console.WriteLine("Coach: 'Why are you by the net?! I said play the field!'");
        }
    }
}