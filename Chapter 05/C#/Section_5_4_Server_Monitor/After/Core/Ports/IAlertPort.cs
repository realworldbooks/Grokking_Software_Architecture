namespace Chapter05.ServerMonitor.After.Core.Ports
{
    /// <summary>
    /// PRIMARY PORT (Driven).
    /// Defines the 'Contract' between the Core and the outside world.
    /// This is the 'Socket' that any Adapter must plug into.
    /// </summary>
    public interface IAlertPort
    {
        /// <summary>
        /// Sends an alert message to an external destination.
        /// </summary>
        void SendAlert(string message);
    }
}