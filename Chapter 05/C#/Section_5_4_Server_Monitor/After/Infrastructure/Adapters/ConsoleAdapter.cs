using Chapter05.ServerMonitor.After.Core.Ports;
using System;

namespace Chapter05.ServerMonitor.After.Infrastructure.Adapters
{
    /// <summary>
    /// ADAPTER 2: The "Dev" Adapter.
    /// Proves that the Core logic remains identical regardless of the 
    /// infrastructure being used (Cloud SMS vs. Local Screen).
    /// </summary>
    public class ConsoleAdapter : IAlertPort
    {
        /// <summary>
        /// Sends an alert to the local console window.
        /// </summary>
        public void SendAlert(string message)
        {
            // Visual feedback for development to help developers track the state.
            Console.ForegroundColor = ConsoleColor.Red;
            Console.WriteLine($"(DEV ADAPTER) ALERT: {message}");
            Console.ResetColor();
        }
    }
}