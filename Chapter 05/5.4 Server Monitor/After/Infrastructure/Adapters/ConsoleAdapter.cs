using System;
using ServerMonitor.After.Core.Ports;

namespace ServerMonitor.After.Infrastructure.Adapters
{
    // ADAPTER 2: The "Dev" Adapter
    public class ConsoleAdapter : IAlertPort
    {
        public void SendAlert(string message)
        {
            Console.ForegroundColor = ConsoleColor.Red; 
            Console.WriteLine($"(DEV ADAPTER) ALERT: {message}"); 
            Console.ResetColor(); 
        }
    }
}