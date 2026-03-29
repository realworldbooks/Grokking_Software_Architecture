using System;

namespace Chapter04.Section_4_3.After
{
    /* * ARCHITECTURAL NOTE: 
     * In the 'After' state, the cross-cutting concern (Logging) has been 
     * abstracted into an interface (ILogger). This allows the OrderService 
     * to remain agnostic of the specific logging implementation.
     */
    public class Demo
    {
        public static void Run()
        {
            Console.WriteLine("--- Running 'After Refactoring' (Injected Logger) ---");
            
            // Dependency Injection (DI) in action: 
            // We decide the logging implementation here at the composition root.
            ILogger logger = new FileLogger();
            var afterService = new OrderService(logger);
            
            afterService.SaveOrder(new Order());
            
            Console.WriteLine("--------------------------------------------");
        }
    }
}