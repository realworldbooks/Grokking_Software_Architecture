using Chapter05.ServerMonitor.After.Core.Ports;
using Chapter05.ServerMonitor.After.Infrastructure.ExternalLibs;

namespace Chapter05.ServerMonitor.After.Infrastructure.Adapters
{
    /// <summary>
    /// THE ADAPTER (Production).
    /// Bridges the internal IAlertPort to the external Twilio SMS service.
    /// </summary>
    public class TwilioAdapter : IAlertPort
    {
        private readonly TwilioClient _client;
        private readonly string _targetPhoneNumber;

        /// <summary>
        /// Initializes a new instance of the TwilioAdapter.
        /// </summary>
        /// <param name="apiKey">The 'Chaotic' infrastructure secret needed for the SDK.</param>
        /// <param name="targetPhoneNumber">The recipient phone number for alerts.</param>
        public TwilioAdapter(string apiKey, string targetPhoneNumber)
        {
            // We instantiate the client once here to satisfy the "Set its value" rule
            // and to improve performance by reusing the same network client.
            _client = new TwilioClient(apiKey);
            _targetPhoneNumber = targetPhoneNumber;
        }

        /// <summary>
        /// Implements the Port by mapping a domain message to an SMS call.
        /// </summary>
        public void SendAlert(string message)
        {
            _client.SendSms(_targetPhoneNumber, message);
        }
    }
}