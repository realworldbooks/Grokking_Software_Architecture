using Chapter14.Section_14_6_InstrumentationLogging.Ports;
using Chapter14.Section_14_6_InstrumentationLogging.Services;
using Xunit;

namespace Chapter14.Tests;

/// <summary>
/// Listing 14.3 — Pattern A: The Minimal Hand-Coded Stub
///
/// Book file: com/ecommerce/order/tests/OrderLambdaStubTests.java
///
/// NOTE: The book's Java example uses an inline lambda (Java interfaces with a
/// single method are functional). C# interfaces are NOT functional types, so we
/// use an equivalent hand-coded concrete stub class (HappyPathPaymentPort)
/// that encapsulates the exact same "always return true" behavior.
/// </summary>
public class OrderLambdaStubTests
{
    [Fact]
    public void TestOrderCheckoutWithInlineStub()
    {
        // Create a sterile, static Test Double with zero network overhead
        IPaymentPort inlinePaymentStub = new HappyPathPaymentPort();
        var service = new OrderService(inlinePaymentStub);

        bool result = service.Checkout("ord_99812", 150.00);
        Assert.True(result, "Checkout failed under a cooperative happy-path stub.");
    }
}
