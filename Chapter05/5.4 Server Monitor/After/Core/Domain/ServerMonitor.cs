using ServerMonitor.After.Core.Ports;

namespace ServerMonitor.After.Core.Domain
{
    // THE INSIDE (The Core)
    // Pure logic. No references to Console, Twilio, or Kafka.
    public class ServerMonitor
    {
        private readonly IAlertPort _alertPort;

        // We "plug in" the adapter via the constructor (Dependency Injection)
        public ServerMonitor(IAlertPort alertPort)
        {
            _alertPort = alertPort;
        }

        public void CheckTemperature(int temp)
        {
            if (temp > 90)
            {
                // The Core just calls the Port. 
                // It doesn't care if it's an SMS, an Email, or a Carrier Pigeon.
                _alertPort.SendAlert($"Temp is {temp} degrees! Take cover!");
            }
            else
            {
                Console.WriteLine($"[Core] Temp {temp} is normal.");
            }
        }
    }
}