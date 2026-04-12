using System;
using System.Threading.Tasks;
using Chapter07.EventCode.Infrastructure;
using Chapter07.EventCode.Controllers;

namespace Chapter07.EventCode
{
    /// <summary>
    /// THE COMPOSITION ROOT: Where the application wires all the decoupled pieces 
    /// together and executes the simulation.
    /// </summary>
    public class Demo
    {
        public static async Task RunAsync()
        {
            Console.WriteLine("=== Section 7.4: Event Definition & Decoupling ===\n");

            // 1. Wire up the Shared Infrastructure (The Broker)
            var broker = new InMemoryBroker();

            // 2. Spin up the Consumer in the background (simulating a separate microservice)
            // We use Task.Run so it operates independently on another thread.
            _ = Task.Run(() => broker.StartListeningAsync());

            // 3. Instantiate the API Service (The Producer)
            var orderController = new OrderController(broker);

            // 4. Simulate the user clicking "Checkout"
            await orderController.CheckoutAsync(Guid.NewGuid(), 149.99m);

            // Wait enough time for the background queue to process the label printing
            await Task.Delay(2000);
            
            Console.WriteLine("Press any key to return to menu...");
            Console.ReadKey();
        }
    }
}