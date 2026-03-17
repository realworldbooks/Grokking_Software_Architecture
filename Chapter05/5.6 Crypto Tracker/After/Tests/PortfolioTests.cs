using System.Threading.Tasks;
using Microsoft.VisualStudio.TestTools.UnitTesting; // Adds the standard MSTest framework
using CryptoTracker.After.Core.Domain;
using CryptoTracker.After.Infrastructure.Adapters;

namespace CryptoTracker.After.Tests
{
    [TestClass]
    public class PortfolioTests
    {
        [TestMethod]
        public async Task Should_Calculate_Value_Correctly()
        {
            // Arrange
            // We use the Fake adapter so we know EXACTLY what the price is ($50,000)
            var fakeAdapter = new FakePriceProvider(50_000m);
            var manager = new PortfolioManager(fakeAdapter);

            // Act
            // We calculate the value for 2 BTC
            var value = await manager.CalculateTotalValue(2m);

            // Assert
            // 2 * 50,000 should be 100,000
            Assert.AreEqual(100_000m, value);
        }
    }
}
