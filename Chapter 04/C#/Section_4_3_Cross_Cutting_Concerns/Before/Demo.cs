using System;

namespace Chapter04.Section_4_3.Before
{
    /* * ARCHITECTURAL NOTE: 
     * In the 'Before' state, the OrderService is likely tightly coupled 
     * to a specific implementation (like a Static Logger or a concrete Database class).
     */
    public class Demo
    {
        public static void Run()
        {
            Console.WriteLine("--- Running 'Before Refactoring' (Static Logger/Tight Coupling) ---");
            
            // In this version, the service handles its own dependencies internally,
            // making it difficult to test or swap implementations.
            var beforeService = new OrderService();
            beforeService.SaveOrder(new Order());
            
            Console.WriteLine("-----------------------------------------");
        }
    }
}