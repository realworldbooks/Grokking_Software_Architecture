namespace Chapter14.Section_14_6_InstrumentationLogging.Ports;

/// <summary>
/// Outbound port definition for third-party billing interactions.
/// Decouples core processing execution from concrete network clients.
///
/// Book listing: com.ecommerce.order.ports.PaymentPort — Listing 14.1
/// </summary>
public interface IPaymentPort
{
    bool Process(double amount);
}