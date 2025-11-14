using System;

namespace Lsp.GoodWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var coach = new Coach();
            Console.WriteLine("Directing field play with a Midfielder:");
            coach.DirectFieldPlay(new Midfielder());
            Console.WriteLine("\nDirecting field play with a Forward:");
            coach.DirectFieldPlay(new Forward());
            Console.WriteLine("\nCoach: 'Perfect!'");
        }
    }
}