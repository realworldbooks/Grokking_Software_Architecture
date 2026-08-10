namespace Chapter14.Section_14_6_InstrumentationLogging.Ports;

/// <summary>
/// A concrete, hand-coded stub implementation of IPaymentPort.
/// Simulates a successful payment with zero network overhead.
/// </summary>
public class HappyPathPaymentPort : IPaymentPort
{
    public bool Process(double amount) => true;
}