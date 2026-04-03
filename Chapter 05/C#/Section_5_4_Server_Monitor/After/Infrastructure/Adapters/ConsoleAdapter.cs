using System;
using Chapter05.ServerMonitor.After.Core.Ports;

namespace Chapter05.ServerMonitor.After.Infrastructure.Adapters
{
    /// <summary>
    /// ADAPTER 2: The "Dev" Adapter.
    /// This adapter is perfect for local development. It proves to Archie that 
    /// the Core doesn't care if the alert goes to a multi-million dollar cloud 
    /// messaging service or simply prints to the local screen.
    /// </summary>
    public class ConsoleAdapter : IAlertPort
    {
        /// <summary>
        /// Implements the port by writing to the local console.
        /// </summary>
        public void SendAlert(string message)
        {
            // We use standard console colors to mimic a real alert,
            // but the Core logic remains completely unaware of this UI detail.
            Console.ForegroundColor = ConsoleColor.Red; 
            Console.WriteLine($"(DEV ADAPTER) ALERT: {message}"); 
            Console.ResetColor(); 
        }
    }
}