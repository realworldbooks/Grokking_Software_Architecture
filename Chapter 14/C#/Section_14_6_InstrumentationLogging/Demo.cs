using Chapter14.Section_14_6_InstrumentationLogging.Ports;
using Chapter14.Section_14_6_InstrumentationLogging.Services;

namespace Chapter14.Section_14_6_InstrumentationLogging;

/// <summary>
/// Demo runner for Section 14.6 — Unit Test and Instrumentation Logging.
///
/// Demonstrates Listings 14.1–14.5:
///   14.1 IPaymentPort (outbound infrastructure port)
///   14.2 OrderService (AsyncLocal thread-local context)
///   14.3 Pattern A: Hand-Coded Stub
///   14.4 Pattern B: Moq Mock
///   14.5 Automated Telemetry Quality Gate
/// </summary>
public static class Demo
{
    public static void Run()
    {
        Console.WriteLine("=== Section 14.6: Unit Test and Instrumentation Logging ===\n");

        // --- Listing 14.1: IPaymentPort (interface defined in Ports folder) ---
        Console.WriteLine("--- Listing 14.1: IPaymentPort (Outbound Infrastructure Port) ---");
        Console.WriteLine("Interface IPaymentPort defines the boundary contract:");
        Console.WriteLine("    bool Process(double amount);");
        Console.WriteLine("Core business logic depends on this abstraction, not a concrete HTTP client.\n");

        // --- Listing 14.2: OrderService with AsyncLocal context ---
        Console.WriteLine("--- Listing 14.2: OrderService (AsyncLocal Thread-Local Context) ---");
        IPaymentPort happyPathPort = new HappyPathPaymentPort();
        var service = new OrderService(happyPathPort);
        bool success = service.Checkout("ord_99812", 150.00);
        Console.WriteLine("Checkout result: " + success);
        Console.WriteLine("AsyncLocal context after checkout (should be null): " + (OrderService.CurrentOrderId ?? "null"));
        Console.WriteLine();

        // --- Listing 14.3: Pattern A — Hand-Coded Stub ---
        // NOTE: The book uses a Java lambda here (Java interfaces with a single
        // method are functional). C# interfaces are NOT functional types, so we
        // use an equivalent concrete stub class (HappyPathPaymentPort).
        Console.WriteLine("--- Listing 14.3: Pattern A — Minimal Hand-Coded Stub ---");
        IPaymentPort inlinePaymentStub = new HappyPathPaymentPort();
        var stubService = new OrderService(inlinePaymentStub);
        bool stubResult = stubService.Checkout("ord_99812", 150.00);
        Console.WriteLine("Hand-coded stub checkout result: " + stubResult);
        Console.WriteLine("(Stub is passive — cannot audit invocation counts.)\n");

        // --- Listing 14.4: Pattern B — Moq Mock (conceptual demo) ---
        Console.WriteLine("--- Listing 14.4: Pattern B — Enterprise Mocking Framework (Moq) ---");
        Console.WriteLine("In the test suite, Moq generates a dynamic proxy:");
        Console.WriteLine("    var mock = new Mock<IPaymentPort>();");
        Console.WriteLine("    mock.Setup(p => p.Process(It.IsAny<double>())).Returns(true);");
        Console.WriteLine("    mock.Verify(p => p.Process(150.00), Times.Once);");
        Console.WriteLine("Moq records invocation history and enforces interaction contracts.\n");

        // --- Listing 14.5: Automated Telemetry Quality Gate (conceptual demo) ---
        Console.WriteLine("--- Listing 14.5: Automated Telemetry Quality Gate ---");
        Console.WriteLine("The test suite intercepts the port boundary to assert AsyncLocal context:");
        Console.WriteLine("    Assert.Equal(\"ord_99812\", OrderService.CurrentOrderId);");
        Console.WriteLine("    Assert.Null(OrderService.CurrentOrderId);  // after checkout");
        Console.WriteLine("This guarantees telemetry compliance on every build.\n");

        Console.WriteLine("=== Demo Complete ===");
        Console.WriteLine("Run 'dotnet test' in Chapter14.Tests to execute the full xUnit + Moq test suite.");
    }
}