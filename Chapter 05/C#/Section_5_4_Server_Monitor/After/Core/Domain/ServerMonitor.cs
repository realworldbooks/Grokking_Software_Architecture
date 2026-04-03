using Chapter05.ServerMonitor.After.Core.Ports;

namespace Chapter05.ServerMonitor.After.Core.Domain
{
    /// <summary>
    /// THE INSIDE (The Core).
    /// This is the Pure Domain Logic. It has been 'Isolated' from the 
    /// infrastructure using the Bulkhead pattern approach for code.
    /// It contains zero references to Console, Twilio, or Kafka.
    /// </summary>
    public class ServerMonitor
    {
        private readonly IAlertPort _alertPort;

        /// <summary>
        /// Constructor Injection.
        /// We "plug in" the adapter via the constructor, allowing the 
        /// Core to remain agnostic of the specific implementation.
        /// </summary>
        public ServerMonitor(IAlertPort alertPort)
        {
            _alertPort = alertPort;
        }

        /// <summary>
        /// Evaluates temperature against domain constants.
        /// </summary>
        public void CheckTemperature(int temp)
        {
            // We use the Global Constant to ensure architectural consistency.
            if (temp > Constants.HIGH_TEMP_THRESHOLD) 
            {
                // The Core just calls the Port. 
                // It acts as the 'Boundary Keeper,' defining 'What' needs to 
                // happen, while leaving the 'How' to the outside world.
                _alertPort.SendAlert($"Temp is {temp} degrees! Take cover!");
            }
            else
            {
                // Note: In a strictly pure Hexagon, even this Console call 
                // would be moved to an Output Port, but we keep it here for simplicity.
                Console.WriteLine($"[Core] Temp {temp} is normal.");
            }
        }
    }
}