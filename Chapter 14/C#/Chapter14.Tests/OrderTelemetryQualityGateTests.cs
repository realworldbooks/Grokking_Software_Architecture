using Chapter14.Section_14_6_InstrumentationLogging.Ports;
using Chapter14.Section_14_6_InstrumentationLogging.Services;
using Xunit;

namespace Chapter14.Tests;

/// <summary>
/// Listing 14.5 — Automated Telemetry Quality Gate
///
/// Book file: com/ecommerce/order/tests/OrderTelemetryQualityGateTests.java (inlined)
/// </summary>
public class OrderTelemetryQualityGateTests
{
    [Fact]
    public void TestCheckout_Should_MaintainAsyncLocalContextBoundaryDuringExecution()
    {
        // Arrange: Intercept interface execution to read Thread-Local variables
        var customInterceptorPort = new InterceptorPaymentPort();

        var service = new OrderService(customInterceptorPort);

        // Act: Trigger the system transaction path
        service.Checkout("ord_99812", 75.00);

        // Assert: Ensure clean thread teardown to prevent memory context leaks
        Assert.Null(OrderService.CurrentOrderId);
    }

    /// <summary>
    /// Hand-coded interceptor stub that reads the AsyncLocal context
    /// mid-transaction to verify telemetry compliance.
    /// </summary>
    private sealed class InterceptorPaymentPort : IPaymentPort
    {
        public bool Process(double amount)
        {
            // Read active thread context values mid-transaction
            string? activeOrderId = OrderService.CurrentOrderId;
            Assert.Equal("ord_99812", activeOrderId);
            return true;
        }
    }
}