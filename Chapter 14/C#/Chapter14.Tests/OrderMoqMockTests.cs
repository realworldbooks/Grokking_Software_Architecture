using Chapter14.Section_14_6_InstrumentationLogging.Ports;
using Chapter14.Section_14_6_InstrumentationLogging.Services;
using Moq;
using Xunit;

namespace Chapter14.Tests;

/// <summary>
/// Listing 14.4 — Pattern B: The Enterprise Mocking Framework (Moq for .NET)
///
/// Book file: com/ecommerce/order/tests/OrderMockitoMockTests.java
/// </summary>
public class OrderMoqMockTests
{
    [Fact]
    public void TestOrderCheckoutWithMoqMock()
    {
        // 1. Arrange: Construct a highly instrumented dynamic proxy via Moq
        var mockPaymentPort = new Mock<IPaymentPort>();
        mockPaymentPort.Setup(p => p.Process(It.IsAny<double>())).Returns(true);
        var service = new OrderService(mockPaymentPort.Object);

        // 2. Act: Trigger the system transaction path
        bool result = service.Checkout("ord_99812", 150.00);

        // 3. Assert & Verify behavioral interaction contracts
        Assert.True(result);
        mockPaymentPort.Verify(p => p.Process(150.00), Times.Once);
    }
}