using System;
using System.Collections.Generic;

namespace Dip.GoodWay
{
    public class Program
    {
        public static void Main(string[] args)
        {
            // The dependencies are created *outside* the Coach
            var team = new List<IPlayer>
            {
                new Forward(),
                new Midfielder(),
                new Winger()
            };
            
            // The Coach is "given" its dependencies (Inversion of Control)
            var coach = new Coach(team);
            coach.ExecuteGamePlan();
        }
    }
}