namespace ServerMonitor.After.Core.Ports
{
    // PRIMARY PORT (Driven)
    // Defines "What" we need, not "How" to do it.
    public interface IAlertPort
    {
        void SendAlert(string message);
    }
}