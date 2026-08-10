using Chapter14.Section_14_6_InstrumentationLogging.Ports;

namespace Chapter14.Section_14_6_InstrumentationLogging.Services;

/// <summary>
/// Instrumented service layer handling transactional checkout workflows.
/// Embeds inside-out semantic telemetry using AsyncLocal thread context.
///
/// Book listing: com.ecommerce.order.services.OrderService — Listing 14.2
/// </summary>
public class OrderService
{
    // .NET equivalent of Java's MDC (Mapped Diagnostic Context):
    // AsyncLocal provides thread-local (and async-flowing) context sandboxing.
    private static readonly AsyncLocal<string?> _orderIdContext = new();

    private readonly IPaymentPort _paymentPort;

    // Loose coupling achieved via constructor dependency injection (IoC)
    public OrderService(IPaymentPort paymentPort)
    {
        _paymentPort = paymentPort;
    }

    public static string? CurrentOrderId => _orderIdContext.Value;

    public bool Checkout(string orderId, double amount)
    {
        // Programmatically bind unique transaction metadata to the thread context
        string? previous = _orderIdContext.Value;
        _orderIdContext.Value = orderId;

        try
        {
            // NOTE: Using Console.WriteLine for simplicity in this demo.
            // In production, use a structured logging library (e.g., Serilog) that
            // automatically enriches log events with AsyncLocal context values.
            Console.WriteLine($"[INFO] Executing transaction payment processing phase | orderId={_orderIdContext.Value}");

            bool paymentSuccess = _paymentPort.Process(amount);

            if (!paymentSuccess)
            {
                Console.WriteLine($"[ERROR] Payment transaction rejected by outbound payment port provider | orderId={_orderIdContext.Value}");
                return false;
            }

            Console.WriteLine($"[INFO] Transaction payment processed successfully | orderId={_orderIdContext.Value}");
            return true;
        }
        finally
        {
            // Restore previous context to prevent memory leaks / data contamination
            _orderIdContext.Value = previous;
        }
    }
}