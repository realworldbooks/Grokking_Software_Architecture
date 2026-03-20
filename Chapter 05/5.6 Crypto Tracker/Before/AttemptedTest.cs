using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace CryptoTracker.Before.Tests
{
    [TestClass]
    public class AttemptedTests
    {
        [TestMethod]
        public void CalculateTotalValue_UsingLiveApi_IsUntestable()
        {
            // Arrange
            // We create the manager, but it creates its own hardcoded HTTP client inside!
            var manager = new PortfolioManager();

            // Act
            // We want to test the value of 1 BTC.
            var value = manager.CalculateTotalValue(1m);

            // ASSERT
            // Problem: What is the price of Bitcoin right now? 
            // Is it $50,000? $60,000? $20,000?
            // We cannot write a reliable assertion because the data changes every second!
            
            // This test is FLAKY. It will almost certainly FAIL because the live 
            // price is rarely exactly 50,000.
            // Furthermore, it will completely CRASH (throw an exception) if the 
            // test runner doesn't have an active internet connection.
            Assert.AreEqual(50_000m, value);
        }
    }
}
