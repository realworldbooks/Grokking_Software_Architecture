using System;

namespace Chapter04.Section_4_2.After
{
    /* * ARCHITECTURAL NOTE: 
     * We renamed 'Program' to 'Demo' and 'Main' to 'Run' 
     * so it can be called dynamically by the root orchestrator.
     */
    public class Demo
    {
        public static void Run()
        {
            Console.WriteLine("--- Running 'After Refactoring' (Downward Dependency) ---");

            // Implementation of the Dependency Inversion Principle (DIP)
            // The high-level Service no longer depends on the low-level SQL implementation.
            IOrderRepository afterRepo = new SqlOrderRepository();
            var afterService = new OrderService(afterRepo);
            
            afterService.SaveOrder(new Order());
            
            Console.WriteLine("----------------------------------------------");
        }
    }
}